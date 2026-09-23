package com.nutrino.timbreassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nutrino.timbreassignment.presentation.navigation.navhost.AppNavHost
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity entry point for the Timbre Assignment application.
 *
 * Configures the Android splash screen, enables edge-to-edge layout rendering,
 * and attaches the root Jetpack Compose hierarchy with [TimbreAssignmentTheme] and [AppNavHost].
 * Annotated with [@AndroidEntryPoint] for Hilt dependency injection initialization.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is starting.
     * Initializes the splash screen, enables edge-to-edge display, and sets the Compose UI content.
     *
     * @param savedInstanceState If non-null, this activity is being re-constructed from a previous saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimbreAssignmentTheme {
                AppNavHost()
            }
        }
    }
}
