package com.nutrino.timbreassignment.presentation.screens.getallvideo.states

import com.nutrino.timbreassignment.data.dataclass.Video

sealed interface GetAllVideoState {
    object Idle : GetAllVideoState
    object Loading : GetAllVideoState
    data class Success(val data: List<Video>) : GetAllVideoState
    data class Error(val message: String) : GetAllVideoState
}
