package com.nutrino.timbreassignment.presentation.screens.homescreen.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Enumeration representing bottom navigation tabs on the Home screen.
 *
 * @property title Display title label.
 * @property icon Vector icon associated with the tab.
 */
enum class HomeTab(
    val title: String,
    val icon: ImageVector
) {
    /** Audio tools tab. */
    AUDIO("Audio", Icons.Default.Audiotrack),

    /** Video tools tab. */
    VIDEO("Video", Icons.Default.Videocam),

    /** App settings tab. */
    SETTINGS("Settings", Icons.Default.Settings)
}
