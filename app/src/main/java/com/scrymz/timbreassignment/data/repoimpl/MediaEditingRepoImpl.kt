package com.scrymz.timbreassignment.data.repoimpl

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.scrymz.timbreassignment.core.states.ResultState
import com.scrymz.timbreassignment.data.dataclass.Song
import com.scrymz.timbreassignment.data.dataclass.Video
import com.scrymz.timbreassignment.domain.repository.MediaEditingRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaEditingRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaEditingRepository {

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
}
