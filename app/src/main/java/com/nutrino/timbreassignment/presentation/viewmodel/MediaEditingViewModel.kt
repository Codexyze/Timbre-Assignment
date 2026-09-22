package com.nutrino.timbreassignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrino.timbreassignment.core.states.ResultState
import com.nutrino.timbreassignment.domain.usecase.GetAllSongsUseCase
import com.nutrino.timbreassignment.domain.usecase.GetAllVideosUseCase
import com.nutrino.timbreassignment.presentation.uistates.GetAllSongState
import com.nutrino.timbreassignment.presentation.uistates.GetAllVideoState
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
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _getAllSongsState = MutableStateFlow<GetAllSongState>(GetAllSongState.Idle)
    val getAllSongsState = _getAllSongsState.asStateFlow()

    private val _getAllVideosState = MutableStateFlow<GetAllVideoState>(GetAllVideoState.Idle)
    val getAllVideosState = _getAllVideosState.asStateFlow()

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
}
