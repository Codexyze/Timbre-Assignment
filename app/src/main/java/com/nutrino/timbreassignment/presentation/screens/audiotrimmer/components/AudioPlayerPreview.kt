package com.nutrino.timbreassignment.presentation.screens.audiotrimmer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun AudioPlayerPreview(
    exoPlayer: ExoPlayer?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    setShowNextButton(false)
                    setShowPreviousButton(false)
                }
            },
            update = { playerView ->
                playerView.player = exoPlayer
            },
            modifier = Modifier.matchParentSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AudioPlayerPreviewPreview() {
    TimbreAssignmentTheme {
        AudioPlayerPreview(exoPlayer = null)
    }
}
