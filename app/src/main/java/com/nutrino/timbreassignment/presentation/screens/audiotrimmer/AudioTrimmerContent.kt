package com.nutrino.timbreassignment.presentation.screens.audiotrimmer

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
import com.nutrino.timbreassignment.presentation.screens.audiotrimmer.components.AudioPlayerPreview
import com.nutrino.timbreassignment.presentation.screens.audiotrimmer.components.OutputFileNameInput
import com.nutrino.timbreassignment.presentation.screens.audiotrimmer.components.TrimErrorView
import com.nutrino.timbreassignment.presentation.screens.audiotrimmer.components.TrimLoadingView
import com.nutrino.timbreassignment.presentation.screens.audiotrimmer.components.TrimRangeSlider
import com.nutrino.timbreassignment.presentation.screens.audiotrimmer.components.TrimSuccessView
import com.nutrino.timbreassignment.presentation.screens.audiotrimmer.states.TrimAudioState
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * UI layout content for the Audio Trimmer screen.
 *
 * @param songTitle Display title of the audio song.
 * @param startMs Start time offset in milliseconds.
 * @param endMs End time offset in milliseconds.
 * @param totalDurationMs Total audio duration in milliseconds.
 * @param fileName Output audio file name string.
 * @param isPlaying Flag indicating active audio playback.
 * @param trimState Current [TrimAudioState] (Idle, Loading, Success, Error).
 * @param exoPlayer [ExoPlayer] instance for audio preview player view.
 * @param onBackClick Navigation pop backstack callback.
 * @param onPlayPauseClick Toggle playback callback.
 * @param onRangeChange Range slider adjustment callback.
 * @param onFileNameChange Output file name edit callback.
 * @param onTrimClick Execute trim action button click.
 * @param onResetTrimState Reset state callback.
 * @param onSaveToFolderClick SAF export action callback.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioTrimmerContent(
    songTitle: String,
    startMs: Long,
    endMs: Long,
    totalDurationMs: Long,
    fileName: String,
    isPlaying: Boolean,
    trimState: TrimAudioState,
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
                title = { Text(text = "Trim Audio - $songTitle") },
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
            AudioPlayerPreview(
                exoPlayer = exoPlayer,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutputFileNameInput(
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
                    is TrimAudioState.Idle -> {
                        Button(
                            onClick = onTrimClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Trim Audio", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                    is TrimAudioState.Loading -> {
                        TrimLoadingView()
                    }
                    is TrimAudioState.Success -> {
                        TrimSuccessView(
                            outputPath = trimState.outputPath,
                            onResetClick = onResetTrimState,
                            onSaveToFolderClick = onSaveToFolderClick
                        )
                    }
                    is TrimAudioState.Error -> {
                        TrimErrorView(
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
private fun AudioTrimmerContentIdlePreview() {
    TimbreAssignmentTheme {
        AudioTrimmerContent(
            songTitle = "Sample Song",
            startMs = 5000L,
            endMs = 30000L,
            totalDurationMs = 60000L,
            fileName = "Trimmed_Sample_Song",
            isPlaying = false,
            trimState = TrimAudioState.Idle,
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
