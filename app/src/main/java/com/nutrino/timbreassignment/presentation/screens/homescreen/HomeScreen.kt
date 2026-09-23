package com.nutrino.timbreassignment.presentation.screens.homescreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nutrino.timbreassignment.core.utils.PermissionUtils
import com.nutrino.timbreassignment.presentation.common.PermissionNotGrantedScreen
import com.nutrino.timbreassignment.presentation.screens.homescreen.components.FeatureCard
import com.nutrino.timbreassignment.presentation.screens.homescreen.components.HomeScreenBottomBar
import com.nutrino.timbreassignment.presentation.screens.homescreen.components.HomeScreenHeader
import com.nutrino.timbreassignment.presentation.screens.homescreen.model.HomeTab
import com.nutrino.timbreassignment.presentation.screens.settings.SettingsScreen
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * Root Composable for the Home Dashboard screen.
 *
 * Hosts top app bar header, bottom navigation bar switching between Audio, Video, and Settings tabs,
 * and feature trigger cards for launching Audio and Video trimming tools.
 *
 * @param modifier Layout modifier.
 * @param onTrimAudioClick Navigation trigger callback for audio trimming flow.
 * @param onTrimVideoClick Navigation trigger callback for video trimming flow.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onTrimAudioClick: () -> Unit = {},
    onTrimVideoClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(PermissionUtils.hasAllMediaPermissions(context)) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermission = permissions.values.all { it } || PermissionUtils.hasAllMediaPermissions(context)
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(PermissionUtils.getRequiredMediaPermissions())
        }
    }

    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.AUDIO) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            HomeScreenHeader()
        },
        bottomBar = {
            HomeScreenBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            when (selectedTab) {
                HomeTab.AUDIO -> {
                    if (!hasPermission) {
                        PermissionNotGrantedScreen(
                            onRequestPermissionClick = {
                                permissionLauncher.launch(PermissionUtils.getRequiredMediaPermissions())
                            }
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            FeatureCard(
                                title = "Trim Audio",
                                icon = Icons.Default.ContentCut,
                                onClick = onTrimAudioClick
                            )
                        }
                    }
                }
                HomeTab.VIDEO -> {
                    if (!hasPermission) {
                        PermissionNotGrantedScreen(
                            onRequestPermissionClick = {
                                permissionLauncher.launch(PermissionUtils.getRequiredMediaPermissions())
                            }
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            FeatureCard(
                                title = "Trim Video",
                                icon = Icons.Default.ContentCut,
                                onClick = onTrimVideoClick
                            )
                        }
                    }
                }
                HomeTab.SETTINGS -> {
                    SettingsScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    TimbreAssignmentTheme {
        HomeScreen()
    }
}
