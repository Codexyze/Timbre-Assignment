package com.nutrino.timbreassignment.presentation.screens.getallvideo.states

import com.nutrino.timbreassignment.data.dataclass.Video

/**
 * UI State interface for the Get All Videos list query workflow.
 */
sealed interface GetAllVideoState {
    /** Idle state before query execution. */
    object Idle : GetAllVideoState

    /** Loading state while videos are being queried from MediaStore. */
    object Loading : GetAllVideoState

    /**
     * Success state holding the queried list of videos.
     *
     * @property data List of retrieved [Video] instances.
     */
    data class Success(val data: List<Video>) : GetAllVideoState

    /**
     * Error state holding failure explanation.
     *
     * @property message Error details.
     */
    data class Error(val message: String) : GetAllVideoState
}
