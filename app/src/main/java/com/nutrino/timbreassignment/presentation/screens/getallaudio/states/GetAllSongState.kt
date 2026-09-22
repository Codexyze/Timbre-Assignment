package com.nutrino.timbreassignment.presentation.screens.getallaudio.states

import com.nutrino.timbreassignment.data.dataclass.Song

sealed interface GetAllSongState {
    object Idle : GetAllSongState
    object Loading : GetAllSongState
    data class Success(val data: List<Song>) : GetAllSongState
    data class Error(val message: String) : GetAllSongState
}
