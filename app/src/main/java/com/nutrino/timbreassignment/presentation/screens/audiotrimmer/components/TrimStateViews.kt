package com.nutrino.timbreassignment.presentation.screens.audiotrimmer.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * Loading indicator card view displayed while trimming is in progress.
 *
 * @param modifier Layout modifier.
 */
@Composable
fun TrimLoadingView(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.padding(end = 12.dp))
            Text(text = "Trimming Audio... Please wait")
        }
    }
}

/**
 * Success card view displayed when trimming completes successfully.
 *
 * @param outputPath Destination URI path string.
 * @param onResetClick Reset trim flow callback.
 * @param onSaveToFolderClick SAF export action callback.
 * @param modifier Layout modifier.
 */
@Composable
fun TrimSuccessView(
    outputPath: String,
    onResetClick: () -> Unit,
    onSaveToFolderClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Success",
                    tint = Color(0xFF4CAF50)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Trimmed Successfully!",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Temp File: $outputPath",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onSaveToFolderClick
                ) {
                    Text("Save to Folder (SAF)")
                }

                OutlinedButton(
                    onClick = onResetClick
                ) {
                    Text("Trim Another")
                }
            }
        }
    }
}

/**
 * Error card view displayed when media trimming fails.
 *
 * @param message Error explanation message.
 * @param onRetryClick Retry callback.
 * @param modifier Layout modifier.
 */
@Composable
fun TrimErrorView(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Trim Failed",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onRetryClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Retry")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TrimLoadingViewPreview() {
    TimbreAssignmentTheme {
        TrimLoadingView()
    }
}

@Preview(showBackground = true)
@Composable
private fun TrimSuccessViewPreview() {
    TimbreAssignmentTheme {
        TrimSuccessView(outputPath = "/storage/emulated/0/Trimmed/song.mp3", onResetClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun TrimErrorViewPreview() {
    TimbreAssignmentTheme {
        TrimErrorView(message = "FFmpeg failed to process media format", onRetryClick = {})
    }
}
