package com.nutrino.timbreassignment.data.dataclass

import android.graphics.Bitmap

/**
 * Data domain model representing an audio song file retrieved from MediaStore.
 *
 * @property id Unique MediaStore content ID.
 * @property path File system path or URI string pointing to the audio file.
 * @property size File size formatted as a string or byte count.
 * @property album Album title associated with the song.
 * @property title Song display title.
 * @property artist Artist name.
 * @property duration Track duration in milliseconds as a string.
 * @property year Release year of the audio track.
 * @property composer Track composer name.
 * @property albumId MediaStore album identifier used to query album artwork.
 * @property albumArt Optional preloaded album cover [Bitmap].
 */
data class Song(
    val id: String,
    val path: String,
    val size: String? = "",
    val album: String? = "Unknown",
    val title: String? = "Unknown",
    val artist: String? = "Unknown",
    val duration: String? = "0",
    val year: String? = "0",
    val composer: String? = "Unknown",
    val albumId: String? = "",
    val albumArt: Bitmap? = null
)
