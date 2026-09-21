package com.scrymz.timbreassignment.domain.usecase

import com.scrymz.timbreassignment.core.states.ResultState
import com.scrymz.timbreassignment.data.dataclass.Video
import com.scrymz.timbreassignment.domain.repository.MediaEditingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllVideosUseCase @Inject constructor(
    private val repository: MediaEditingRepository
) {
    suspend operator fun invoke(): Flow<ResultState<List<Video>>> {
        return repository.getAllVideos()
    }
}
