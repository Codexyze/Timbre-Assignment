package com.nutrino.timbreassignment.presentation.screens.settings

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.nutrino.timbreassignment.core.utils.PermissionUtils
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * Settings tab screen displaying app permission statuses and shortcuts to open System Application Details Settings.
 *
 * @param modifier Layout modifier.
 */
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val isMediaAllowed = PermissionUtils.hasAllMediaPermissions(context)
    val isNotificationAllowed = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "App Permissions",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        PermissionStatusItem(
            title = "Media Storage Permission",
            icon = Icons.Default.Folder,
            isAllowed = isMediaAllowed,
            onClick = {
                openAppSettings(context)
            }
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Spacer(modifier = Modifier.height(12.dp))
            PermissionStatusItem(
                title = "Notifications Permission",
                icon = Icons.Default.Notifications,
                isAllowed = isNotificationAllowed,
                onClick = {
                    openAppSettings(context)
                }
            )
        }
    }
}

/**
 * List item card displaying individual permission status and navigation affordance.
 *
 * @param title Permission description label.
 * @param icon Icon representing the permission category.
 * @param isAllowed Boolean status of the permission grant.
 * @param onClick Click handler to launch System Settings.
 * @param modifier Layout modifier.
 */
@Composable
fun PermissionStatusItem(
    title: String,
    icon: ImageVector,
    isAllowed: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (!isAllowed) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            if (isAllowed) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Allowed",
                        tint = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Allowed",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF4CAF50)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Open Settings",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    TimbreAssignmentTheme {
        SettingsScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun PermissionStatusItemAllowedPreview() {
    TimbreAssignmentTheme {
        PermissionStatusItem(
            title = "Media Storage Permission",
            icon = Icons.Default.Folder,
            isAllowed = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PermissionStatusItemNotAllowedPreview() {
    TimbreAssignmentTheme {
        PermissionStatusItem(
            title = "Media Storage Permission",
            icon = Icons.Default.Folder,
            isAllowed = false,
            onClick = {}
        )
    }
}
