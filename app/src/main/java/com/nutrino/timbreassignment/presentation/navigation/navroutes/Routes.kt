package com.nutrino.timbreassignment.presentation.navigation.navroutes

import kotlinx.serialization.Serializable

/** Navigation route destination for the Home Screen. */
@Serializable
object HOMESCREEN

/** Navigation route destination for the Audio List Selection Screen. */
@Serializable
object ALLAUDIOSCREEN

/** Navigation route destination for the Video List Selection Screen. */
@Serializable
object ALLVIDEOSCREEN

/**
 * Navigation route destination for the Audio Trimmer Screen with song arguments.
 *
 * @property songPath Source song URI or file path.
 * @property songTitle Display title of the audio track.
 * @property songDurationMs Total track duration in milliseconds.
 */
@Serializable
data class AUDIOTRIMMERSCREEN(
    val songPath: String,
    val songTitle: String,
    val songDurationMs: Long
)

/**
 * Navigation route destination for the Video Trimmer Screen with video arguments.
 *
 * @property videoPath Source video URI or file path.
 * @property videoTitle Display title of the video file.
 * @property videoDurationMs Total video duration in milliseconds.
 */
@Serializable
data class VIDEOTRIMMERSCREEN(
    val videoPath: String,
    val videoTitle: String,
    val videoDurationMs: Long
)
