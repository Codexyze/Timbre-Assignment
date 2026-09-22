package com.nutrino.timbreassignment.presentation.screens.getallaudio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

@Composable
fun NoAudioFoundView(
    modifier: Modifier = Modifier,
    message: String = "No Audio Files Found",
    description: String = "We couldn't find any audio files on your device.",
    onRefresh: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MusicOff,
                contentDescription = "No Audio",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (onRefresh != null) {
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(onClick = onRefresh) {
                Text(text = "Refresh")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoAudioFoundViewPreview() {
    TimbreAssignmentTheme {
        NoAudioFoundView(onRefresh = {})
    }
}
