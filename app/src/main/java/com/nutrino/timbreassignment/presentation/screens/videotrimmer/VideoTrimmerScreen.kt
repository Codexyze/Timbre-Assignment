package com.nutrino.timbreassignment.presentation.screens.videotrimmer

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nutrino.timbreassignment.presentation.viewmodel.MediaEditingViewModel
import com.nutrino.timbreassignment.presentation.viewmodel.MediaViewModel
import kotlinx.coroutines.delay

/**
 * Screen component for trimming video files.
 *
 * Provides ExoPlayer video player preview, range slider start/end time selection,
 * output file naming, and Storage Access Framework document export.
 *
 * @param videoPath Source video file path or URI string.
 * @param videoTitle Display title of the video file.
 * @param videoDurationMs Total video duration in milliseconds.
 * @param onBackClick Navigation pop backstack action.
 * @param mediaViewModel [MediaViewModel] managing video preview playback.
 * @param viewModel [MediaEditingViewModel] executing video trimming requests.
 */
@Composable
fun VideoTrimmerScreen(
    videoPath: String,
    videoTitle: String,
    videoDurationMs: Long,
    onBackClick: () -> Unit = {},
    mediaViewModel: MediaViewModel = hiltViewModel(),
    viewModel: MediaEditingViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var startMs by rememberSaveable { mutableLongStateOf(0L) }
    var endMs by rememberSaveable { mutableLongStateOf(if (videoDurationMs > 0) videoDurationMs else 30000L) }
    var fileName by rememberSaveable { mutableStateOf("Trimmed_$videoTitle") }
    var isPlaying by remember { mutableStateOf(false) }

    val trimState by viewModel.trimVideoState.collectAsStateWithLifecycle()

    val ext = remember(videoPath) { videoPath.substringAfterLast('.', "mp4") }
    val mimeType = remember(ext) { if (ext.equals("mp4", ignoreCase = true)) "video/mp4" else "video/*" }

    val safLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument(mimeType)
    ) { destinationUri ->
        destinationUri?.let { uri ->
            viewModel.trimVideo(
                inputPath = videoPath,
                outputPath = uri.toString(),
                startMs = startMs,
                endMs = endMs
            )
        }
    }

    LaunchedEffect(videoPath) {
        if (videoPath.isNotEmpty()) {
            val uri = if (videoPath.startsWith("content://") || videoPath.startsWith("file://")) {
                android.net.Uri.parse(videoPath)
            } else {
                android.net.Uri.fromFile(java.io.File(videoPath))
            }
            mediaViewModel.initializePlayer(uri)
        }
    }

    LaunchedEffect(isPlaying, endMs) {
        if (isPlaying) {
            val player = mediaViewModel.getPlayer()
            while (isPlaying && player.isPlaying) {
                if (player.currentPosition >= endMs) {
                    player.pause()
                    isPlaying = false
                    break
                }
                delay(100)
            }
        }
    }

    VideoTrimmerContent(
        videoTitle = videoTitle,
        startMs = startMs,
        endMs = endMs,
        totalDurationMs = if (videoDurationMs > 0) videoDurationMs else 60000L,
        fileName = fileName,
        isPlaying = isPlaying,
        trimState = trimState,
        exoPlayer = mediaViewModel.getPlayer(),
        onBackClick = onBackClick,
        onPlayPauseClick = {
            val player = mediaViewModel.getPlayer()
            if (player.isPlaying) {
                player.pause()
                isPlaying = false
            } else {
                if (player.currentPosition >= endMs || player.currentPosition < startMs) {
                    player.seekTo(startMs)
                }
                player.play()
                isPlaying = true
            }
        },
        onRangeChange = { newStartMs, newEndMs ->
            val oldStart = startMs
            val oldEnd = endMs
            startMs = newStartMs
            endMs = newEndMs

            if (newStartMs != oldStart) {
                mediaViewModel.seekTo(newStartMs)
            } else if (newEndMs != oldEnd) {
                mediaViewModel.pause()
                isPlaying = false
            }
        },
        onFileNameChange = { newName ->
            fileName = newName
        },
        onTrimClick = {
            if (fileName.isBlank()) {
                Toast.makeText(context, "Please enter output file name", Toast.LENGTH_SHORT).show()
                return@VideoTrimmerContent
            }
            if (startMs >= endMs) {
                Toast.makeText(context, "Invalid start/end trim range", Toast.LENGTH_SHORT).show()
                return@VideoTrimmerContent
            }

            safLauncher.launch("${fileName.trim()}.$ext")
        },
        onResetTrimState = {
            viewModel.resetTrimVideoState()
        },
        onSaveToFolderClick = {
            if (fileName.isBlank()) {
                Toast.makeText(context, "Please enter output file name", Toast.LENGTH_SHORT).show()
                return@VideoTrimmerContent
            }
            safLauncher.launch("${fileName.trim()}.$ext")
        }
    )
}
