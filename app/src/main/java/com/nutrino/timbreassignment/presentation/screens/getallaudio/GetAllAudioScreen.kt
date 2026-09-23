package com.nutrino.timbreassignment.presentation.screens.getallaudio

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
import com.nutrino.timbreassignment.data.dataclass.Song
import com.nutrino.timbreassignment.presentation.common.PermissionNotGrantedScreen
import com.nutrino.timbreassignment.presentation.screens.getallaudio.states.GetAllSongState
import com.nutrino.timbreassignment.presentation.viewmodel.MediaEditingViewModel
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * Screen component for selecting an audio song track from device storage.
 *
 * Handles runtime permission requests and binds state from [MediaEditingViewModel].
 *
 * @param onBackClick Navigation pop backstack callback.
 * @param onSongClick Callback triggered when the user selects a song item.
 * @param viewModel ViewModel collecting song list states.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllAudioScreen(
    onBackClick: () -> Unit = {},
    onSongClick: (Song) -> Unit = {},
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
            viewModel.getAllSongs()
        }
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(PermissionUtils.getRequiredMediaPermissions())
        }
    }

    val state by viewModel.getAllSongsState.collectAsStateWithLifecycle()

    if (!hasPermission) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Select Audio") },
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
        GetAllAudioContent(
            state = state,
            onBackClick = onBackClick,
            onSongClick = onSongClick,
            onRetry = { viewModel.getAllSongs() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GetAllAudioScreenPreview() {
    TimbreAssignmentTheme {
        GetAllAudioContent(
            state = GetAllSongState.Success(
                data = listOf(
                    Song(id = "1", path = "", title = "Preview Song 1", artist = "Artist 1"),
                    Song(id = "2", path = "", title = "Preview Song 2", artist = "Artist 2")
                )
            )
        )
    }
}
