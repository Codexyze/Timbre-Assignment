package com.nutrino.timbreassignment.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CrimsonColorPalette = darkColorScheme(
    primary = CrimsonAccent,
    onPrimary = AppBlack,
    primaryContainer = AppBlack,
    onPrimaryContainer = AppWhite,

    secondary = CrimsonAccent,
    onSecondary = AppBlack,
    secondaryContainer = AppBlack,
    onSecondaryContainer = AppWhite,

    tertiary = CrimsonAccent,
    onTertiary = AppBlack,
    tertiaryContainer = AppBlack,
    onTertiaryContainer = AppWhite,

    background = AppBlack,
    onBackground = AppWhite,
    surface = AppBlack,
    onSurface = AppWhite,
    surfaceVariant = AppBlack,
    onSurfaceVariant = AppWhite,

    inverseSurface = AppBlack,
    inverseOnSurface = AppWhite,
    inversePrimary = CrimsonAccent,

    error = Color(0xFFFF5252),
    onError = AppBlack,
    errorContainer = AppBlack,
    onErrorContainer = Color(0xFFFF5252),

    outline = CrimsonAccent,
    outlineVariant = CrimsonAccent,
    scrim = AppBlack
)

@Composable
fun TimbreAssignmentTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CrimsonColorPalette,
        typography = Typography,
        content = content
    )
}
