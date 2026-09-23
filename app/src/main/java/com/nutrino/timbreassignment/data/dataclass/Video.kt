package com.nutrino.timbreassignment.data.dataclass

/**
 * Data domain model representing a video file retrieved from MediaStore.
 *
 * @property id Unique MediaStore video record ID.
 * @property path Absolute file system path or content URI string.
 * @property duration Total video duration in milliseconds formatted as a string.
 * @property thumbnail Content URI or file path pointing to the video thumbnail image.
 * @property fileName Display file name including extension.
 * @property title Video title extracted from metadata or file name.
 * @property folderName Containing directory name.
 */
data class Video(
    val id: String,
    val path: String,
    val duration: String,
    val thumbnail: String,
    val fileName: String,
    val title: String,
    val folderName: String
)
