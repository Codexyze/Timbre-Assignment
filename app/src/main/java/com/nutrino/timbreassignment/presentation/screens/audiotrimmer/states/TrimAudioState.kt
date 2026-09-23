package com.nutrino.timbreassignment.presentation.screens.audiotrimmer.states

/**
 * UI State interface for the audio trimming operation.
 */
sealed interface TrimAudioState {
    /** Idle state before trimming is triggered. */
    object Idle : TrimAudioState

    /** Loading state while FFmpeg executes audio trimming. */
    object Loading : TrimAudioState

    /**
     * Success state holding the trimmed output file destination URI/path.
     *
     * @property outputPath Destination URI string.
     */
    data class Success(val outputPath: String) : TrimAudioState

    /**
     * Error state holding trim failure message.
     *
     * @property message Failure details.
     */
    data class Error(val message: String) : TrimAudioState
}
