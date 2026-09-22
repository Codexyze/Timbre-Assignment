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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaEditingViewModel @Inject constructor(
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val getAllVideosUseCase: GetAllVideosUseCase,
    private val trimAudioUseCase: TrimAudioUseCase,
    private val trimVideoUseCase: TrimVideoUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _getAllSongsState = MutableStateFlow<GetAllSongState>(GetAllSongState.Idle)
    val getAllSongsState = _getAllSongsState.asStateFlow()

    private val _getAllVideosState = MutableStateFlow<GetAllVideoState>(GetAllVideoState.Idle)
    val getAllVideosState = _getAllVideosState.asStateFlow()

    private val _trimAudioState = MutableStateFlow<TrimAudioState>(TrimAudioState.Idle)
    val trimAudioState = _trimAudioState.asStateFlow()

    private val _trimVideoState = MutableStateFlow<TrimVideoState>(TrimVideoState.Idle)
    val trimVideoState = _trimVideoState.asStateFlow()

    init {
        getAllSongs()
        getAllVideos()
    }

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

    fun resetTrimAudioState() {
        _trimAudioState.value = TrimAudioState.Idle
    }

    fun resetTrimVideoState() {
        _trimVideoState.value = TrimVideoState.Idle
    }
}
