package com.scrymz.timbreassignment.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import com.scrymz.timbreassignment.core.media.MediaPlayerManager
import com.scrymz.timbreassignment.domain.repository.MediaEditingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaEditingRepository: MediaEditingRepository,
    private val mediaPlayerManager: MediaPlayerManager
) : ViewModel() {

    fun initializePlayer(uri: Uri) {
        mediaPlayerManager.initializePlayer(uri)
    }

    fun getPlayer(): ExoPlayer {
        return mediaPlayerManager.getPlayer()
    }

    fun play() {
        mediaPlayerManager.play()
    }

    fun pause() {
        mediaPlayerManager.pause()
    }

    fun seekTo(positionMs: Long) {
        mediaPlayerManager.seekTo(positionMs)
    }

    fun releasePlayer() {
        mediaPlayerManager.releasePlayer()
    }

    override fun onCleared() {
        mediaPlayerManager.releasePlayer()
    }
}
