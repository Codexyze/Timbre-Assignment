package com.nutrino.timbreassignment.presentation.screens.getallaudio.states

import com.nutrino.timbreassignment.data.dataclass.Song

/**
 * UI State interface for the Get All Songs list query workflow.
 */
sealed interface GetAllSongState {
    /** Idle state before query execution. */
    object Idle : GetAllSongState

    /** Loading state while songs are being queried from MediaStore. */
    object Loading : GetAllSongState

    /**
     * Success state holding the queried list of songs.
     *
     * @property data List of retrieved [Song] instances.
     */
    data class Success(val data: List<Song>) : GetAllSongState

    /**
     * Error state holding failure explanation.
     *
     * @property message Error details.
     */
    data class Error(val message: String) : GetAllSongState
}
