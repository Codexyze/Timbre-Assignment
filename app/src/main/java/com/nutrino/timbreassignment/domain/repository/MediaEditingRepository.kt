package com.nutrino.timbreassignment.domain.repository

import com.nutrino.timbreassignment.core.states.ResultState
import com.nutrino.timbreassignment.data.dataclass.Song
import com.nutrino.timbreassignment.data.dataclass.Video
import kotlinx.coroutines.flow.Flow

/**
 * Domain repository contract for media querying and editing operations.
 */
interface MediaEditingRepository {

    /**
     * Retrieves all song tracks available on the device.
     *
     * @return [Flow] emitting [ResultState] with a list of [Song] objects.
     */
    suspend fun getAllSongs(): Flow<ResultState<List<Song>>>

    /**
     * Retrieves all video files available on the device.
     *
     * @return [Flow] emitting [ResultState] with a list of [Video] objects.
     */
    suspend fun getAllVideos(): Flow<ResultState<List<Video>>>

    /**
     * Trims an audio file from [startMs] to [endMs] and saves the output to [outputPath].
     *
     * @param inputPath Source audio file path or URI string.
     * @param outputPath Destination output URI string or file path.
     * @param startMs Start trim offset in milliseconds.
     * @param endMs End trim offset in milliseconds.
     * @return [Flow] emitting [ResultState] with the resulting file path or error message.
     */
    suspend fun trimAudio(inputPath: String, outputPath: String, startMs: Long, endMs: Long): Flow<ResultState<String>>

    /**
     * Trims a video file from [startMs] to [endMs] and saves the output to [outputPath].
     *
     * @param inputPath Source video file path or URI string.
     * @param outputPath Destination output URI string or file path.
     * @param startMs Start trim offset in milliseconds.
     * @param endMs End trim offset in milliseconds.
     * @return [Flow] emitting [ResultState] with the resulting file path or error message.
     */
    suspend fun trimVideo(inputPath: String, outputPath: String, startMs: Long, endMs: Long): Flow<ResultState<String>>
}
