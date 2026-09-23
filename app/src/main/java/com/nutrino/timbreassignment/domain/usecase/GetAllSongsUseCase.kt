package com.nutrino.timbreassignment.domain.usecase

import com.nutrino.timbreassignment.core.states.ResultState
import com.nutrino.timbreassignment.data.dataclass.Song
import com.nutrino.timbreassignment.domain.repository.MediaEditingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Domain use case for retrieving all audio songs from device storage.
 *
 * @property repository Injected [MediaEditingRepository].
 */
class GetAllSongsUseCase @Inject constructor(
    private val repository: MediaEditingRepository
) {
    /**
     * Executes the use case to query all device songs.
     *
     * @return [Flow] emitting [ResultState] with a list of [Song] items.
     */
    suspend operator fun invoke(): Flow<ResultState<List<Song>>> {
        return repository.getAllSongs()
    }
}
