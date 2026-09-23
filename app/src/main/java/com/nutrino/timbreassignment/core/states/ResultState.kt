package com.nutrino.timbreassignment.core.states

/**
 * Encapsulates the execution state of an asynchronous operation or data query.
 *
 * @param T The type of data encapsulated upon success.
 */
sealed class ResultState<out T> {

    /** Represents an ongoing or pending asynchronous operation. */
    object Loading : ResultState<Nothing>()

    /**
     * Represents a successful operation holding the resulting [data].
     *
     * @property data The successfully retrieved data payload.
     */
    data class Success<T>(val data: T) : ResultState<T>()

    /**
     * Represents a failed operation holding an error [message].
     *
     * @property message Descriptive failure explanation or localized error text.
     */
    data class Error(val message: String) : ResultState<Nothing>()
}
