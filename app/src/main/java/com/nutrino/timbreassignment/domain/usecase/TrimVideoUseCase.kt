package com.nutrino.timbreassignment.domain.usecase

import com.nutrino.timbreassignment.core.states.ResultState
import com.nutrino.timbreassignment.domain.repository.MediaEditingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrimVideoUseCase @Inject constructor(
    private val repository: MediaEditingRepository
) {
    suspend operator fun invoke(
        inputPath: String,
        outputPath: String = "",
        startMs: Long,
        endMs: Long
    ): Flow<ResultState<String>> {
        return repository.trimVideo(
            inputPath = inputPath,
            outputPath = outputPath,
            startMs = startMs,
            endMs = endMs
        )
    }
}
