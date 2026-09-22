package com.nutrino.timbreassignment.domain.usecase

import com.nutrino.timbreassignment.core.states.ResultState
import com.nutrino.timbreassignment.data.dataclass.Video
import com.nutrino.timbreassignment.domain.repository.MediaEditingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllVideosUseCase @Inject constructor(
    private val repository: MediaEditingRepository
) {
    suspend operator fun invoke(): Flow<ResultState<List<Video>>> {
        return repository.getAllVideos()
    }
}
