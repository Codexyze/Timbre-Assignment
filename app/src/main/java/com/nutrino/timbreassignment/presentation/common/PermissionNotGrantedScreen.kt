package com.nutrino.timbreassignment.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * Screen presented when required media permissions have not been granted by the user.
 *
 * Provides a clear explanation and a button trigger to request permission.
 *
 * @param onRequestPermissionClick Callback function triggered when the grant permission button is clicked.
 * @param modifier Optional [Modifier] for screen container styling.
 * @param title Header title explaining permission requirement.
 * @param description Detailed permission usage explanation.
 */
@Composable
fun PermissionNotGrantedScreen(
    onRequestPermissionClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Storage Permission Required",
    description: String = "To view and edit your audio and video files, please grant media storage permissions."
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.FolderOff,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRequestPermissionClick) {
            Text(text = "Grant Permission")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PermissionNotGrantedScreenPreview() {
    TimbreAssignmentTheme {
        PermissionNotGrantedScreen(onRequestPermissionClick = {})
    }
}
