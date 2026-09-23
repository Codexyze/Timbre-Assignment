package com.nutrino.timbreassignment.domain.usecase

import com.nutrino.timbreassignment.core.states.ResultState
import com.nutrino.timbreassignment.data.dataclass.Video
import com.nutrino.timbreassignment.domain.repository.MediaEditingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Domain use case for retrieving all video files from device storage.
 *
 * @property repository Injected [MediaEditingRepository].
 */
class GetAllVideosUseCase @Inject constructor(
    private val repository: MediaEditingRepository
) {
    /**
     * Executes the use case to query all device videos.
     *
     * @return [Flow] emitting [ResultState] with a list of [Video] items.
     */
    suspend operator fun invoke(): Flow<ResultState<List<Video>>> {
        return repository.getAllVideos()
    }
}
