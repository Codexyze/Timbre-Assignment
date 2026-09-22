package com.nutrino.timbreassignment.presentation.screens.videotrimmer.states

sealed interface TrimVideoState {
    object Idle : TrimVideoState
    object Loading : TrimVideoState
    data class Success(val outputPath: String) : TrimVideoState
    data class Error(val message: String) : TrimVideoState
}
