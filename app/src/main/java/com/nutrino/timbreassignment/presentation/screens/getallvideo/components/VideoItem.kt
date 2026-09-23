package com.nutrino.timbreassignment.presentation.screens.getallvideo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nutrino.timbreassignment.data.dataclass.Video
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * List item card displaying video details including title and containing folder.
 *
 * @param video The [Video] model object.
 * @param onClick Selection click callback.
 * @param modifier Layout modifier.
 */
@Composable
fun VideoItem(
    video: Video,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Videocam,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = video.title.ifEmpty { video.fileName.ifEmpty { "Unknown Video" } },
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (video.folderName.isNotEmpty()) {
                    Text(
                        text = video.folderName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VideoItemPreview() {
    TimbreAssignmentTheme {
        VideoItem(
            video = Video(
                id = "1",
                path = "/storage/emulated/0/Movies/sample.mp4",
                duration = "120000",
                thumbnail = "",
                fileName = "sample.mp4",
                title = "Sample Video",
                folderName = "Movies"
            ),
            onClick = {}
        )
    }
}
