package com.nutrino.timbreassignment.presentation.navigation.navroutes

import kotlinx.serialization.Serializable

@Serializable
object HOMESCREEN

@Serializable
object ALLAUDIOSCREEN

@Serializable
object ALLVIDEOSCREEN

@Serializable
data class AUDIOTRIMMERSCREEN(
    val songPath: String,
    val songTitle: String,
    val songDurationMs: Long
)

@Serializable
data class VIDEOTRIMMERSCREEN(
    val videoPath: String,
    val videoTitle: String,
    val videoDurationMs: Long
)
