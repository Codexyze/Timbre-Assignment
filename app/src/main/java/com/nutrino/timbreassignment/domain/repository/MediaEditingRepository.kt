package com.nutrino.timbreassignment.domain.repository

import com.nutrino.timbreassignment.core.states.ResultState
import com.nutrino.timbreassignment.data.dataclass.Song
import com.nutrino.timbreassignment.data.dataclass.Video
import kotlinx.coroutines.flow.Flow

interface MediaEditingRepository {
    suspend fun getAllSongs(): Flow<ResultState<List<Song>>>
    suspend fun getAllVideos(): Flow<ResultState<List<Video>>>
    suspend fun trimAudio(inputPath: String, outputPath: String, startMs: Long, endMs: Long): Flow<ResultState<String>>
    suspend fun trimVideo(inputPath: String, outputPath: String, startMs: Long, endMs: Long): Flow<ResultState<String>>
}
