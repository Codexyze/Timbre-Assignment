package com.nutrino.timbreassignment.presentation.screens.videotrimmer.states

/**
 * UI State interface for the video trimming operation.
 */
sealed interface TrimVideoState {
    /** Idle state before video trimming. */
    object Idle : TrimVideoState

    /** Loading state while FFmpeg executes video trimming. */
    object Loading : TrimVideoState

    /**
     * Success state holding the trimmed output video destination path/URI.
     *
     * @property outputPath Destination URI string.
     */
    data class Success(val outputPath: String) : TrimVideoState

    /**
     * Error state holding trim failure details.
     *
     * @property message Failure explanation.
     */
    data class Error(val message: String) : TrimVideoState
}
