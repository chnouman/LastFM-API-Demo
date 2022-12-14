package com.chnouman.lastfmapidemo.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.domain.usecases.album.GetAlbumInfo
import com.chnouman.lastfmapidemo.domain.usecases.track.GetLocalTracks
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getLocalTracks: GetLocalTracks,
    private val getAlbumInfo: GetAlbumInfo
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getTracks(albumName: String, artistName: String) {
        viewModelScope.launch(Dispatchers.IO) {
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
        data class Success(val artists: List<Track>?) : UIEvent()
        data class Error(val message: String) : UIEvent()
    }
}
