package com.chnouman.lastfmapidemo.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.domain.usecases.track.GetLocalTracks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getLocalTracks: GetLocalTracks,
    private val getAlbumInfo: com.chnouman.lastfmapidemo.domain.usecases.GetAlbumInfo,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun getTracks(albumName: String, artistName: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            getLocalTracks(albumName)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (result.data?.isEmpty() == true) {
                                getAlbumInfo(artistName, albumName)
                                    .collectLatest {
                                        _eventFlow.emit(UIEvent.Success(it.data))
                                    }
                            } else {
                                _eventFlow.emit(UIEvent.Success(result.data))
                            }
                        }
                        is Resource.Error -> {
                            result.message?.let { UIEvent.Error(it) }?.let {
                                _eventFlow.emit(
                                    it
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _eventFlow.emit(UIEvent.Loading)
                        }
                    }
                }.launchIn(this)
        }
    }

    sealed class UIEvent {
        object Loading : UIEvent()
        data class Success(val artists: MutableList<Track>?) : UIEvent()
        data class Error(val message: String) : UIEvent()
    }
}