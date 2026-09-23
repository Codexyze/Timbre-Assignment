package com.nutrino.timbreassignment.core.media

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Centralized manager wrapping Media3 [ExoPlayer] playback operations.
 *
 * Automatically manages ExoPlayer instance instantiation, media source preparation,
 * playback control (play, pause, seek), and clean player disposal upon lifecycle destruction.
 *
 * @property context Application context used to build new ExoPlayer instances.
 */
@Singleton
class MediaPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var exoPlayer: ExoPlayer? = null

    /**
     * Retrieves or lazily creates the active [ExoPlayer] instance for Compose UI surface binding.
     *
     * @return The active non-null [ExoPlayer].
     */
    fun getPlayer(): ExoPlayer {
        val activePlayer = exoPlayer ?: ExoPlayer.Builder(context).build().also {
            exoPlayer = it
        }
        return activePlayer
    }

    /**
     * Prepares the ExoPlayer with a media file identified by [uri].
     * Reuses or instantiates the active player and prepares the media item immediately.
     *
     * @param uri [Uri] pointing to the target audio or video resource.
     */
    fun initializePlayer(uri: Uri) {
        val player = getPlayer()
        player.apply {
            stop()
            clearMediaItems()
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = false
        }
    }

    /**
     * Begins or resumes media playback.
     */
    fun play() {
        exoPlayer?.play()
    }

    /**
     * Pauses media playback.
     */
    fun pause() {
        exoPlayer?.pause()
    }

    /**
     * Seeks playback to the specified time in milliseconds.
     *
     * @param positionMs Target playback timestamp in milliseconds.
     */
    fun seekTo(positionMs: Long) {
        exoPlayer?.seekTo(positionMs)
    }

    /**
     * Stops playback and releases ExoPlayer resources.
     */
    fun releasePlayer() {
        exoPlayer?.apply {
            stop()
            clearMediaItems()
            release()
        }
        exoPlayer = null
    }
}
