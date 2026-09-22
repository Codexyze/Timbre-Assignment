package com.nutrino.timbreassignment.presentation.screens.audiotrimmer.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

@Composable
fun TrimRangeSlider(
    startMs: Long,
    endMs: Long,
    totalDurationMs: Long,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onRangeChange: (startMs: Long, endMs: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    @SuppressLint("DefaultLocale")
    fun formatTime(millis: Long): String {
        val totalSec = millis / 1000
        val minutes = totalSec / 60
        val seconds = totalSec % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Trim Range",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPlayPauseClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            RangeSlider(
                value = startMs.toFloat()..endMs.toFloat(),
                onValueChange = { range ->
                    onRangeChange(range.start.toLong(), range.endInclusive.toLong())
                },
                valueRange = 0f..maxOf(totalDurationMs.toFloat(), 1f),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Start: ${formatTime(startMs)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "End: ${formatTime(endMs)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TrimRangeSliderPreview() {
    TimbreAssignmentTheme {
        TrimRangeSlider(
            startMs = 5000L,
            endMs = 30000L,
            totalDurationMs = 60000L,
            isPlaying = false,
            onPlayPauseClick = {},
            onRangeChange = { _, _ -> }
        )
    }
}
