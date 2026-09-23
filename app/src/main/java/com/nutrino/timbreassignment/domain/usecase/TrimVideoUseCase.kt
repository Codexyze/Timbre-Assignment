package com.nutrino.timbreassignment.domain.usecase

import com.nutrino.timbreassignment.core.states.ResultState
import com.nutrino.timbreassignment.domain.repository.MediaEditingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Domain use case for executing video trimming operations between start and end timestamps.
 *
 * @property repository Injected [MediaEditingRepository].
 */
class TrimVideoUseCase @Inject constructor(
    private val repository: MediaEditingRepository
) {
    /**
     * Executes video trimming for the specified file and duration window.
     *
     * @param inputPath Source file path or URI.
     * @param outputPath Output file path or Storage Access Framework document URI.
     * @param startMs Start time in milliseconds.
     * @param endMs End time in milliseconds.
     * @return [Flow] emitting [ResultState] with the generated output path string or error.
     */
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
