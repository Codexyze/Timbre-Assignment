package com.scrymz.timbreassignment.presentation.uistates

import com.scrymz.timbreassignment.data.dataclass.Song
import com.scrymz.timbreassignment.data.dataclass.Video

sealed interface GetAllSongState {
    object Idle : GetAllSongState
    object Loading : GetAllSongState
    data class Success(val data: List<Song>) : GetAllSongState
    data class Error(val message: String) : GetAllSongState
}

sealed interface GetAllVideoState {
    object Idle : GetAllVideoState
    object Loading : GetAllVideoState
    data class Success(val data: List<Video>) : GetAllVideoState
    data class Error(val message: String) : GetAllVideoState
}
