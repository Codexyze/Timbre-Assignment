package com.nutrino.timbreassignment.presentation.screens.getallvideo

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nutrino.timbreassignment.core.utils.PermissionUtils
import com.nutrino.timbreassignment.data.dataclass.Video
import com.nutrino.timbreassignment.presentation.common.PermissionNotGrantedScreen
import com.nutrino.timbreassignment.presentation.screens.getallvideo.states.GetAllVideoState
import com.nutrino.timbreassignment.presentation.viewmodel.MediaEditingViewModel
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllVideoScreen(
    onBackClick: () -> Unit = {},
    onVideoClick: (Video) -> Unit = {},
    viewModel: MediaEditingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(PermissionUtils.hasAllMediaPermissions(context)) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.all { it } || PermissionUtils.hasAllMediaPermissions(context)
        hasPermission = granted
        if (granted) {
            viewModel.getAllVideos()
        }
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(PermissionUtils.getRequiredMediaPermissions())
        }
    }

    val state by viewModel.getAllVideosState.collectAsStateWithLifecycle()

    if (!hasPermission) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Select Video") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            PermissionNotGrantedScreen(
                onRequestPermissionClick = {
                    permissionLauncher.launch(PermissionUtils.getRequiredMediaPermissions())
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    } else {
        GetAllVideoContent(
            state = state,
            onBackClick = onBackClick,
            onVideoClick = onVideoClick,
            onRetry = { viewModel.getAllVideos() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GetAllVideoScreenPreview() {
    TimbreAssignmentTheme {
        GetAllVideoContent(
            state = GetAllVideoState.Success(
                data = listOf(
                    Video(id = "1", path = "", duration = "", thumbnail = "", fileName = "vid1.mp4", title = "Preview Video 1", folderName = "Movies"),
                    Video(id = "2", path = "", duration = "", thumbnail = "", fileName = "vid2.mp4", title = "Preview Video 2", folderName = "Downloads")
                )
            )
        )
    }
}
