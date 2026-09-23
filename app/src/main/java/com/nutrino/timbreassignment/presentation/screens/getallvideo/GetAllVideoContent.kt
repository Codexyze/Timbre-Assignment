package com.nutrino.timbreassignment.presentation.screens.getallvideo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nutrino.timbreassignment.data.dataclass.Video
import com.nutrino.timbreassignment.presentation.common.ErrorScreen
import com.nutrino.timbreassignment.presentation.common.LoadingScreen
import com.nutrino.timbreassignment.presentation.screens.getallvideo.components.NoVideoFoundView
import com.nutrino.timbreassignment.presentation.screens.getallvideo.components.VideoItem
import com.nutrino.timbreassignment.presentation.screens.getallvideo.states.GetAllVideoState
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * Layout content displaying a searchable list of videos from device storage.
 *
 * @param state Current [GetAllVideoState] emitting video query results.
 * @param onBackClick Navigation back callback.
 * @param onVideoClick Video item click selection callback.
 * @param onRetry Retry callback on query error.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllVideoContent(
    state: GetAllVideoState,
    onBackClick: () -> Unit = {},
    onVideoClick: (Video) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state) {
                is GetAllVideoState.Idle, is GetAllVideoState.Loading -> {
                    LoadingScreen()
                }
                is GetAllVideoState.Success -> {
                    if (state.data.isEmpty()) {
                        NoVideoFoundView(
                            onRefresh = onRetry
                        )
                    } else {
                        Column(modifier = Modifier.fillMaxSize()) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                placeholder = { Text(text = "Search videos...") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search"
                                    )
                                },
                                trailingIcon = {
                                    if (searchQuery.isNotEmpty()) {
                                        IconButton(onClick = { searchQuery = "" }) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = "Clear Search"
                                            )
                                        }
                                    }
                                },
                                singleLine = true,
                                shape = MaterialTheme.shapes.medium
                            )

                            val filteredVideos = remember(searchQuery, state.data) {
                                if (searchQuery.isBlank()) {
                                    state.data
                                } else {
                                    val query = searchQuery.trim()
                                    state.data.filter { video ->
                                        video.title.contains(query, ignoreCase = true) ||
                                                video.fileName.contains(query, ignoreCase = true) ||
                                                video.folderName.contains(query, ignoreCase = true)
                                    }
                                }
                            }

                            if (filteredVideos.isEmpty()) {
                                NoVideoFoundView(
                                    message = "No Matching Videos",
                                    description = "No video files match \"$searchQuery\""
                                )
                            } else {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(filteredVideos, key = { it.id }) { video ->
                                        VideoItem(
                                            video = video,
                                            onClick = { onVideoClick(video) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                is GetAllVideoState.Error -> {
                    ErrorScreen(
                        message = state.message,
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GetAllVideoContent_LoadingPreview() {
    TimbreAssignmentTheme {
        GetAllVideoContent(state = GetAllVideoState.Loading)
    }
}

@Preview(showBackground = true)
@Composable
private fun GetAllVideoContent_SuccessPreview() {
    TimbreAssignmentTheme {
        GetAllVideoContent(
            state = GetAllVideoState.Success(
                data = listOf(
                    Video(id = "1", path = "", duration = "", thumbnail = "", fileName = "vid1.mp4", title = "Sample Video 1", folderName = "Movies"),
                    Video(id = "2", path = "", duration = "", thumbnail = "", fileName = "vid2.mp4", title = "Sample Video 2", folderName = "Downloads")
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GetAllVideoContent_ErrorPreview() {
    TimbreAssignmentTheme {
        GetAllVideoContent(
            state = GetAllVideoState.Error("Failed to fetch video files from device")
        )
    }
}
