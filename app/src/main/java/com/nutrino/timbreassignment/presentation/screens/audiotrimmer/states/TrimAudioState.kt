package com.nutrino.timbreassignment.presentation.screens.audiotrimmer.states

sealed interface TrimAudioState {
    object Idle : TrimAudioState
    object Loading : TrimAudioState
    data class Success(val outputPath: String) : TrimAudioState
    data class Error(val message: String) : TrimAudioState
}
