package com.nutrino.timbreassignment.core.media

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import javax.inject.Inject

class MediaPlayerManager @Inject constructor(
    private val exoPlayer: ExoPlayer
) {
    fun getPlayer(): ExoPlayer {
        return exoPlayer
    }

    fun initializePlayer(uri: Uri) {
        exoPlayer.apply {
            stop()
            clearMediaItems()
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = false
        }
    }

    fun play() {
        exoPlayer.play()
    }

    fun pause() {
        exoPlayer.pause()
    }

    fun seekTo(positionMs: Long) {
        exoPlayer.seekTo(positionMs)
    }

    fun releasePlayer() {
        exoPlayer.apply {
            stop()
            release()
        }
    }
}
