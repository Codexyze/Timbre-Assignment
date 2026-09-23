package com.nutrino.timbreassignment.presentation.screens.getallaudio

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
import com.nutrino.timbreassignment.data.dataclass.Song
import com.nutrino.timbreassignment.presentation.common.ErrorScreen
import com.nutrino.timbreassignment.presentation.common.LoadingScreen
import com.nutrino.timbreassignment.presentation.screens.getallaudio.components.NoAudioFoundView
import com.nutrino.timbreassignment.presentation.screens.getallaudio.components.SongItem
import com.nutrino.timbreassignment.presentation.screens.getallaudio.states.GetAllSongState
import com.nutrino.timbreassignment.ui.theme.TimbreAssignmentTheme

/**
 * UI content layout for searching and filtering the list of songs retrieved from storage.
 *
 * @param state Current [GetAllSongState] emitting loading, success, or error state.
 * @param onBackClick Navigation pop backstack action.
 * @param onSongClick Callback triggered when a song item is clicked.
 * @param onRetry Retry callback on failure or refresh.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllAudioContent(
    state: GetAllSongState,
    onBackClick: () -> Unit = {},
    onSongClick: (Song) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state) {
                is GetAllSongState.Idle, is GetAllSongState.Loading -> {
                    LoadingScreen()
                }
                is GetAllSongState.Success -> {
                    if (state.data.isEmpty()) {
                        NoAudioFoundView(
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
                                placeholder = { Text(text = "Search songs...") },
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

                            val filteredSongs = remember(searchQuery, state.data) {
                                if (searchQuery.isBlank()) {
                                    state.data
                                } else {
                                    val query = searchQuery.trim()
                                    state.data.filter { song ->
                                        (song.title?.contains(query, ignoreCase = true) == true) ||
                                                (song.artist?.contains(query, ignoreCase = true) == true) ||
                                                (song.album?.contains(query, ignoreCase = true) == true)
                                    }
                                }
                            }

                            if (filteredSongs.isEmpty()) {
                                NoAudioFoundView(
                                    message = "No Matching Songs",
                                    description = "No audio files match \"$searchQuery\""
                                )
                            } else {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(filteredSongs, key = { it.id }) { song ->
                                        SongItem(
                                            song = song,
                                            onClick = { onSongClick(song) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                is GetAllSongState.Error -> {
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
private fun GetAllAudioContent_LoadingPreview() {
    TimbreAssignmentTheme {
        GetAllAudioContent(state = GetAllSongState.Loading)
    }
}

@Preview(showBackground = true)
@Composable
private fun GetAllAudioContent_SuccessPreview() {
    TimbreAssignmentTheme {
        GetAllAudioContent(
            state = GetAllSongState.Success(
                data = listOf(
                    Song(id = "1", path = "", title = "Sample Song 1", artist = "Artist A"),
                    Song(id = "2", path = "", title = "Sample Song 2", artist = "Artist B")
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GetAllAudioContent_ErrorPreview() {
    TimbreAssignmentTheme {
        GetAllAudioContent(
            state = GetAllSongState.Error("Failed to fetch audio files from device")
        )
    }
}
