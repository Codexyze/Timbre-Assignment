package com.nutrino.timbreassignment.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import com.nutrino.timbreassignment.core.media.MediaPlayerManager
import com.nutrino.timbreassignment.domain.repository.MediaEditingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * [ViewModel] managing playback lifecycle and player controls for audio and video media previews.
 *
 * Interacts with [MediaPlayerManager] to expose playback commands (play, pause, seek, release).
 *
 * @property mediaEditingRepository Injected repository instance.
 * @property mediaPlayerManager Injected player manager instance wrapping ExoPlayer.
 */
@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaEditingRepository: MediaEditingRepository,
    private val mediaPlayerManager: MediaPlayerManager
) : ViewModel() {

    /**
     * Initializes the player with the given media source [uri].
     *
     * @param uri [Uri] pointing to the media file.
     */
    fun initializePlayer(uri: Uri) {
        mediaPlayerManager.initializePlayer(uri)
    }

    /**
     * Retrieves the active [ExoPlayer] instance.
     *
     * @return Underlying [ExoPlayer].
     */
    fun getPlayer(): ExoPlayer {
        return mediaPlayerManager.getPlayer()
    }

    /**
     * Starts or resumes playback.
     */
    fun play() {
        mediaPlayerManager.play()
    }

    /**
     * Pauses active playback.
     */
    fun pause() {
        mediaPlayerManager.pause()
    }

    /**
     * Seeks playback to [positionMs].
     *
     * @param positionMs Target timestamp in milliseconds.
     */
    fun seekTo(positionMs: Long) {
        mediaPlayerManager.seekTo(positionMs)
    }

    /**
     * Stops and releases player resources.
     */
    fun releasePlayer() {
        mediaPlayerManager.releasePlayer()
    }

    /**
     * Automatically called when ViewModel is destroyed to release media player resources.
     */
    override fun onCleared() {
        mediaPlayerManager.releasePlayer()
    }
}
