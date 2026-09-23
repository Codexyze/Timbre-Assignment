package com.nutrino.timbreassignment.presentation.screens.videotrimmer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer
import com.nutrino.timbreassignment.presentation.screens.audiotrimmer.components.TrimRangeSlider
import com.nutrino.timbreassignment.presentation.screens.videotrimmer.components.VideoOutputFileNameInput
import com.nutrino.timbreassignment.presentation.screens.videotrimmer.components.VideoPlayerPreview
import com.nutrino.timbreassignment.presentation.screens.videotrimmer.components.VideoTrimErrorView
import com.nutrino.timbreassignment.presentation.screens.videotrimmer.components.VideoTrimLoadingView
import com.nutrino.timbreassignment.presentation.screens.videotrimmer.components.VideoTrimSuccessView
import com.nutrino.timbreassignment.presentation.screens.videotrimmer.states.TrimVideoState
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * UI layout content for the Video Trimmer screen.
 *
 * @param videoTitle Display title of the video file.
 * @param startMs Start trim offset in milliseconds.
 * @param endMs End trim offset in milliseconds.
 * @param totalDurationMs Total video duration in milliseconds.
 * @param fileName Output video file name string.
 * @param isPlaying Flag indicating active video playback.
 * @param trimState Current [TrimVideoState].
 * @param exoPlayer [ExoPlayer] instance for video preview rendering.
 * @param onBackClick Navigation pop backstack callback.
 * @param onPlayPauseClick Play/Pause toggle callback.
 * @param onRangeChange Range slider adjustment callback.
 * @param onFileNameChange File name edit callback.
 * @param onTrimClick Execute trim action button click.
 * @param onResetTrimState Reset trim state callback.
 * @param onSaveToFolderClick SAF export document callback.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoTrimmerContent(
    videoTitle: String,
    startMs: Long,
    endMs: Long,
    totalDurationMs: Long,
    fileName: String,
    isPlaying: Boolean,
    trimState: TrimVideoState,
    exoPlayer: ExoPlayer?,
    onBackClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onRangeChange: (startMs: Long, endMs: Long) -> Unit,
    onFileNameChange: (String) -> Unit,
    onTrimClick: () -> Unit,
    onResetTrimState: () -> Unit,
    onSaveToFolderClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Trim Video - $videoTitle") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            VideoPlayerPreview(
                exoPlayer = exoPlayer,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                VideoOutputFileNameInput(
                    fileName = fileName,
                    onFileNameChange = onFileNameChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                TrimRangeSlider(
                    startMs = startMs,
                    endMs = endMs,
                    totalDurationMs = totalDurationMs,
                    isPlaying = isPlaying,
                    onPlayPauseClick = onPlayPauseClick,
                    onRangeChange = onRangeChange
                )

                Spacer(modifier = Modifier.height(20.dp))

                when (trimState) {
                    is TrimVideoState.Idle -> {
                        Button(
                            onClick = onTrimClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Trim Video", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                    is TrimVideoState.Loading -> {
                        VideoTrimLoadingView()
                    }
                    is TrimVideoState.Success -> {
                        VideoTrimSuccessView(
                            outputPath = trimState.outputPath,
                            onResetClick = onResetTrimState,
                            onSaveToFolderClick = onSaveToFolderClick
                        )
                    }
                    is TrimVideoState.Error -> {
                        VideoTrimErrorView(
                            message = trimState.message,
                            onRetryClick = onTrimClick
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VideoTrimmerContentIdlePreview() {
    TimbreAssignmentTheme {
        VideoTrimmerContent(
            videoTitle = "Sample Video",
            startMs = 5000L,
            endMs = 30000L,
            totalDurationMs = 60000L,
            fileName = "Trimmed_Sample_Video",
            isPlaying = false,
            trimState = TrimVideoState.Idle,
            exoPlayer = null,
            onBackClick = {},
            onPlayPauseClick = {},
            onRangeChange = { _, _ -> },
            onFileNameChange = {},
            onTrimClick = {},
            onResetTrimState = {}
        )
    }
}
