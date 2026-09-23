package com.nutrino.timbreassignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrino.timbreassignment.core.states.ResultState
import com.nutrino.timbreassignment.domain.usecase.GetAllSongsUseCase
import com.nutrino.timbreassignment.domain.usecase.GetAllVideosUseCase
import com.nutrino.timbreassignment.domain.usecase.TrimAudioUseCase
import com.nutrino.timbreassignment.domain.usecase.TrimVideoUseCase
import com.nutrino.timbreassignment.presentation.screens.audiotrimmer.states.TrimAudioState
import com.nutrino.timbreassignment.presentation.screens.getallaudio.states.GetAllSongState
import com.nutrino.timbreassignment.presentation.screens.getallvideo.states.GetAllVideoState
import com.nutrino.timbreassignment.presentation.screens.videotrimmer.states.TrimVideoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main [ViewModel] responsible for orchestrating media list queries and media trimming workflows.
 *
 * Exposes UI states via [StateFlow]s for song listings, video listings, audio trimming, and video trimming.
 *
 * @property getAllSongsUseCase Injected use case for querying device songs.
 * @property getAllVideosUseCase Injected use case for querying device videos.
 * @property trimAudioUseCase Injected use case for audio trimming.
 * @property trimVideoUseCase Injected use case for video trimming.
 * @property ioDispatcher Injected IO dispatcher for background execution.
 */
@HiltViewModel
class MediaEditingViewModel @Inject constructor(
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val getAllVideosUseCase: GetAllVideosUseCase,
    private val trimAudioUseCase: TrimAudioUseCase,
    private val trimVideoUseCase: TrimVideoUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _getAllSongsState = MutableStateFlow<GetAllSongState>(GetAllSongState.Idle)

    /** StateFlow emitting the current list state of song tracks. */
    val getAllSongsState: StateFlow<GetAllSongState> = _getAllSongsState.asStateFlow()

    private val _getAllVideosState = MutableStateFlow<GetAllVideoState>(GetAllVideoState.Idle)

    /** StateFlow emitting the current list state of video files. */
    val getAllVideosState: StateFlow<GetAllVideoState> = _getAllVideosState.asStateFlow()

    private val _trimAudioState = MutableStateFlow<TrimAudioState>(TrimAudioState.Idle)

    /** StateFlow emitting the current audio trim execution status. */
    val trimAudioState: StateFlow<TrimAudioState> = _trimAudioState.asStateFlow()

    private val _trimVideoState = MutableStateFlow<TrimVideoState>(TrimVideoState.Idle)

    /** StateFlow emitting the current video trim execution status. */
    val trimVideoState: StateFlow<TrimVideoState> = _trimVideoState.asStateFlow()

    init {
        getAllSongs()
        getAllVideos()
    }

    /**
     * Fetches all audio songs from device storage.
     */
    fun getAllSongs() {
        viewModelScope.launch(ioDispatcher) {
            getAllSongsUseCase().collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _getAllSongsState.value = GetAllSongState.Loading
                    }
                    is ResultState.Success -> {
                        _getAllSongsState.value = GetAllSongState.Success(result.data)
                    }
                    is ResultState.Error -> {
                        _getAllSongsState.value = GetAllSongState.Error(result.message)
                    }
                }
            }
        }
    }

    /**
     * Fetches all video files from device storage.
     */
    fun getAllVideos() {
        viewModelScope.launch(ioDispatcher) {
            getAllVideosUseCase().collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _getAllVideosState.value = GetAllVideoState.Loading
                    }
                    is ResultState.Success -> {
                        _getAllVideosState.value = GetAllVideoState.Success(result.data)
                    }
                    is ResultState.Error -> {
                        _getAllVideosState.value = GetAllVideoState.Error(result.message)
                    }
                }
            }
        }
    }

    /**
     * Trims an audio file between [startMs] and [endMs].
     *
     * @param inputPath Source file path.
     * @param outputPath Target document destination URI.
     * @param startMs Start time in milliseconds.
     * @param endMs End time in milliseconds.
     */
    fun trimAudio(
        inputPath: String,
        outputPath: String = "",
        startMs: Long,
        endMs: Long
    ) {
        viewModelScope.launch(ioDispatcher) {
            trimAudioUseCase(
                inputPath = inputPath,
                outputPath = outputPath,
                startMs = startMs,
                endMs = endMs
            ).collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _trimAudioState.value = TrimAudioState.Loading
                    }
                    is ResultState.Success -> {
                        _trimAudioState.value = TrimAudioState.Success(result.data)
                    }
                    is ResultState.Error -> {
                        _trimAudioState.value = TrimAudioState.Error(result.message)
                    }
                }
            }
        }
    }

    /**
     * Trims a video file between [startMs] and [endMs].
     *
     * @param inputPath Source file path.
     * @param outputPath Target document destination URI.
     * @param startMs Start time in milliseconds.
     * @param endMs End time in milliseconds.
     */
    fun trimVideo(
        inputPath: String,
        outputPath: String = "",
        startMs: Long,
        endMs: Long
    ) {
        viewModelScope.launch(ioDispatcher) {
            trimVideoUseCase(
                inputPath = inputPath,
                outputPath = outputPath,
                startMs = startMs,
                endMs = endMs
            ).collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _trimVideoState.value = TrimVideoState.Loading
                    }
                    is ResultState.Success -> {
                        _trimVideoState.value = TrimVideoState.Success(result.data)
                    }
                    is ResultState.Error -> {
                        _trimVideoState.value = TrimVideoState.Error(result.message)
                    }
                }
            }
        }
    }

    /** Resets the audio trim state to [TrimAudioState.Idle]. */
    fun resetTrimAudioState() {
        _trimAudioState.value = TrimAudioState.Idle
    }

    /** Resets the video trim state to [TrimVideoState.Idle]. */
    fun resetTrimVideoState() {
        _trimVideoState.value = TrimVideoState.Idle
    }
}
