package com.scrymz.timbreassignment.domain.usecase

import com.scrymz.timbreassignment.core.states.ResultState
import com.scrymz.timbreassignment.data.dataclass.Song
import com.scrymz.timbreassignment.domain.repository.MediaEditingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSongsUseCase @Inject constructor(
    private val repository: MediaEditingRepository
) {
    suspend operator fun invoke(): Flow<ResultState<List<Song>>> {
        return repository.getAllSongs()
    }
}
