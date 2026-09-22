package com.nutrino.timbreassignment.presentation.screens.audiotrimmer

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

@Composable
fun AudioTrimmerScreen(
    songPath: String,
    songTitle: String,
    songDurationMs: Long,
    onBackClick: () -> Unit = {},
    mediaViewModel: MediaViewModel = hiltViewModel(),
    viewModel: MediaEditingViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var startMs by rememberSaveable { mutableLongStateOf(0L) }
    var endMs by rememberSaveable { mutableLongStateOf(if (songDurationMs > 0) songDurationMs else 30000L) }
    var fileName by rememberSaveable { mutableStateOf("Trimmed_$songTitle") }
    var isPlaying by remember { mutableStateOf(false) }

    val trimState by viewModel.trimAudioState.collectAsStateWithLifecycle()

    val ext = remember(songPath) { songPath.substringAfterLast('.', "mp3") }
    val mimeType = remember(ext) { if (ext.equals("mp3", ignoreCase = true)) "audio/mpeg" else "audio/*" }

    val safLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument(mimeType)
    ) { destinationUri ->
        destinationUri?.let { uri ->
            viewModel.trimAudio(
                inputPath = songPath,
                outputPath = uri.toString(),
                startMs = startMs,
                endMs = endMs
            )
        }
    }

    LaunchedEffect(songPath) {
        if (songPath.isNotEmpty()) {
            val uri = if (songPath.startsWith("content://") || songPath.startsWith("file://")) {
                android.net.Uri.parse(songPath)
            } else {
                android.net.Uri.fromFile(java.io.File(songPath))
            }
            mediaViewModel.initializePlayer(uri)
        }
    }

    LaunchedEffect(isPlaying, endMs) {
        if (isPlaying) {
            val player = mediaViewModel.getPlayer()
            while (isPlaying) {
                if (player.currentPosition >= endMs) {
                    player.pause()
                    break
                }
            }
        }
    }

    AudioTrimmerContent(
        songTitle = songTitle,
        startMs = startMs,
        endMs = endMs,
        totalDurationMs = if (songDurationMs > 0) songDurationMs else 60000L,
        fileName = fileName,
        isPlaying = isPlaying,
        trimState = trimState,
        exoPlayer = mediaViewModel.getPlayer(),
        onBackClick = onBackClick,
        onPlayPauseClick = {
            val player = mediaViewModel.getPlayer()
            if (player.isPlaying) {
                player.pause()
            } else {
                if (player.currentPosition >= endMs || player.currentPosition < startMs) {
                    player.seekTo(startMs)
                }
                player.play()
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
            }
        },
        onFileNameChange = { newName ->
            fileName = newName
        },
        onTrimClick = {
            if (fileName.isBlank()) {
                Toast.makeText(context, "Please enter output file name", Toast.LENGTH_SHORT).show()
                return@AudioTrimmerContent
            }
            if (startMs >= endMs) {
                Toast.makeText(context, "Invalid start/end trim range", Toast.LENGTH_SHORT).show()
                return@AudioTrimmerContent
            }

            safLauncher.launch("${fileName.trim()}.$ext")
        },
        onResetTrimState = {
            viewModel.resetTrimAudioState()
        },
        onSaveToFolderClick = {
            if (fileName.isBlank()) {
                Toast.makeText(context, "Please enter output file name", Toast.LENGTH_SHORT).show()
                return@AudioTrimmerContent
            }
            safLauncher.launch("${fileName.trim()}.$ext")
        }
    )
}
