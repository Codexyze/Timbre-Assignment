package com.scrymz.timbreassignment.domain.repository

import com.scrymz.timbreassignment.core.states.ResultState
import com.scrymz.timbreassignment.data.dataclass.Song
import com.scrymz.timbreassignment.data.dataclass.Video
import kotlinx.coroutines.flow.Flow

interface MediaEditingRepository {
    suspend fun getAllSongs(): Flow<ResultState<List<Song>>>
    suspend fun getAllVideos(): Flow<ResultState<List<Video>>>
}
