package com.nutrino.timbreassignment.presentation.screens.homescreen.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.ui.graphics.vector.ImageVector

enum class HomeTab(
    val title: String,
    val icon: ImageVector
) {
    AUDIO("Audio", Icons.Default.Audiotrack),
    VIDEO("Video", Icons.Default.Videocam),
    SETTINGS("Settings", Icons.Default.Settings)
}
