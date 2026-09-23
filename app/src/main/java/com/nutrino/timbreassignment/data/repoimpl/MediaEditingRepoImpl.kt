package com.nutrino.timbreassignment.data.repoimpl

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.ReturnCode
import com.nutrino.timbreassignment.core.states.ResultState
import com.nutrino.timbreassignment.data.dataclass.Song
import com.nutrino.timbreassignment.data.dataclass.Video
import com.nutrino.timbreassignment.domain.repository.MediaEditingRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Locale
import javax.inject.Inject
import androidx.core.net.toUri

/**
 * Production implementation of [MediaEditingRepository].
 *
 * Interacts with system [MediaStore] content resolvers to query device audio and video files,
 * and executes FFmpeg commands via FFmpegKit to trim audio and video files efficiently without re-encoding.
 *
 * @property context Application context injected via Hilt for content resolver access.
 */
class MediaEditingRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaEditingRepository {

    /**
     * Queries device storage via [MediaStore.Audio.Media.EXTERNAL_CONTENT_URI] for all available audio tracks.
     *
     * @return [Flow] emitting [ResultState.Loading], followed by [ResultState.Success] holding the list of [Song]s,
     * or [ResultState.Error] on failure.
     */
    override suspend fun getAllSongs(): Flow<ResultState<List<Song>>> = flow {
        val songs = mutableListOf<Song>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.COMPOSER,
            MediaStore.Audio.Media.ALBUM_ID
        )
        val contentResolver = context.contentResolver
        val selection = null
        emit(ResultState.Loading)
        try {
            val cursor = contentResolver.query(uri, projection, selection, null, null)
            cursor?.use { cursorelement ->
                while (cursorelement.moveToNext()) {
                    val id = cursorelement.getString(0) ?: ""
                    val path = cursorelement.getString(1) ?: ""
                    val size = cursorelement.getString(2) ?: ""
                    val album = cursorelement.getString(3) ?: "Unknown"
                    val title = cursorelement.getString(4) ?: "Unknown"
                    val artist = cursorelement.getString(5) ?: "Unknown"
                    val duration = cursorelement.getString(6) ?: "0"
                    val year = cursorelement.getString(7) ?: "0"
                    val composer = cursorelement.getString(8) ?: "Unknown"
                    val albumID = cursorelement.getString(9) ?: ""
                    val song = Song(
                        id = id,
                        path = path,
                        size = size,
                        album = album,
                        title = title,
                        artist = artist,
                        duration = duration,
                        year = year,
                        composer = composer,
                        albumId = albumID
                    )
                    songs.add(song)
                }
            }
            emit(ResultState.Success(data = songs))
        } catch (e: Exception) {
            emit(ResultState.Error(message = e.message.toString()))
        }
    }

    /**
     * Queries device storage via [MediaStore.Video.Media.EXTERNAL_CONTENT_URI] for all available video files.
     *
     * @return [Flow] emitting [ResultState.Loading], followed by [ResultState.Success] holding the list of [Video]s,
     * or [ResultState.Error] on failure.
     */
    override suspend fun getAllVideos(): Flow<ResultState<List<Video>>> = flow {
        val videoFiles = mutableListOf<Video>()
        emit(ResultState.Loading)
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.DISPLAY_NAME
        )
        val contentResolver = context.contentResolver
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val cursor = contentResolver.query(uri, projection, null, null, null)
        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(0) ?: ""
                    val path = cursor.getString(1) ?: ""
                    val duration = cursor.getString(2) ?: "0"
                    val title = cursor.getString(3) ?: "Unknown"
                    val fileName = cursor.getString(4) ?: "Unknown"
                    val thumbnail = if (id.isNotEmpty()) {
                        ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            id.toLongOrNull() ?: 0L
                        ).toString()
                    } else ""

                    val folderName = path.substringBeforeLast('/')
                        .substringAfterLast('/')

                    val videoFile = Video(
                        id = id,
                        path = path,
                        duration = duration,
                        thumbnail = thumbnail,
                        fileName = fileName,
                        title = title,
                        folderName = folderName
                    )

                    videoFiles.add(videoFile)
                }
                cursor.close()
                emit(ResultState.Success(videoFiles))
            } else {
                emit(ResultState.Error("No Video Found.."))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Error loading videos"))
        } finally {
            cursor?.close()
        }
    }

    /**
     * Trims an audio file using FFmpeg stream copy between [startMs] and [endMs].
     *
     * @param inputPath Source audio file path or URI.
     * @param outputPath Destination output URI string (SAF Uri or file path).
     * @param startMs Start trim offset in milliseconds.
     * @param endMs End trim offset in milliseconds.
     * @return [Flow] emitting progress and output path upon completion.
     */
    override suspend fun trimAudio(
        inputPath: String,
        outputPath: String,
        startMs: Long,
        endMs: Long
    ): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)
        try {
            val startSec = String.format(Locale.US, "%.3f", startMs / 1000.0)
            val endSec = String.format(Locale.US, "%.3f", endMs / 1000.0)

            val ext = inputPath.substringAfterLast('.', "mp3")
            val tempFile = java.io.File(context.cacheDir, "trimmed_temp_${System.currentTimeMillis()}.$ext")

            val cmd = "-y -ss $startSec -to $endSec -i \"$inputPath\" -c copy \"${tempFile.absolutePath}\""
            val session = FFmpegKit.execute(cmd)

            if (ReturnCode.isSuccess(session.returnCode)) {
                if (outputPath.startsWith("content://")) {
                    val uri = outputPath.toUri()
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        tempFile.inputStream().use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    tempFile.delete()
                }
                emit(ResultState.Success(outputPath))
            } else {
                tempFile.delete()
                emit(ResultState.Error("Failed to trim audio"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error during audio trimming"))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Trims a video file using FFmpeg stream copy between [startMs] and [endMs].
     *
     * @param inputPath Source video file path or URI.
     * @param outputPath Destination output URI string.
     * @param startMs Start trim offset in milliseconds.
     * @param endMs End trim offset in milliseconds.
     * @return [Flow] emitting progress and output path upon completion.
     */
    override suspend fun trimVideo(
        inputPath: String,
        outputPath: String,
        startMs: Long,
        endMs: Long
    ): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)
        try {
            val startSec = String.format(Locale.US, "%.3f", startMs / 1000.0)
            val endSec = String.format(Locale.US, "%.3f", endMs / 1000.0)

            val ext = inputPath.substringAfterLast('.', "mp4")
            val tempFile = java.io.File(context.cacheDir, "trimmed_temp_${System.currentTimeMillis()}.$ext")

            val cmd = "-y -ss $startSec -to $endSec -i \"$inputPath\" -c copy \"${tempFile.absolutePath}\""
            val session = FFmpegKit.execute(cmd)

            if (ReturnCode.isSuccess(session.returnCode)) {
                if (outputPath.startsWith("content://")) {
                    val uri = outputPath.toUri()
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        tempFile.inputStream().use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    tempFile.delete()
                }
                emit(ResultState.Success(outputPath))
            } else {
                tempFile.delete()
                emit(ResultState.Error("Failed to trim video"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error during video trimming"))
        }
    }.flowOn(Dispatchers.IO)
}
