package com.chnouman.lastfmapidemo.presentation.search.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chnouman.lastfmapidemo.BuildConfig
import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.remote.LastFMApi
import com.chnouman.lastfmapidemo.domain.usecases.SearchArtist
import com.chnouman.lastfmapidemo.presentation.search.paging.LastFMPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val searchArtistUseCase: SearchArtist,
    private val lastFMApi: LastFMApi
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun searchArtist(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            Log.d("TAG", "search: thread is ${Thread.currentThread().name}")
            delay(500L)
            searchArtistUseCase(query)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _eventFlow.emit(UIEvent.Success(result.data?.map {
                                com.chnouman.lastfmapidemo.data.local.entities.Artist(
                                    it.name,
                                    it.url, it.listeners, it.image
                                )
                            }
                            ))
                        }
                        is Resource.Error -> {
                            result.message?.let { UIEvent.Error(it) }?.let {
                                _eventFlow.emit(UIEvent.Error(it.message ?: "Unknown error"))
                            }
                        }
                        is Resource.Loading -> {
                            _eventFlow.emit(UIEvent.Loading)
                        }
                    }
                }.launchIn(this)
        }
    }

    fun searchArtistPaged(artistQuery: String): Flow<PagingData<com.chnouman.lastfmapidemo.data.local.entities.Artist>> {
        return Pager(PagingConfig(pageSize = 1)) {
            LastFMPagingSource(artistQuery, BuildConfig.API_KEY, lastFMApi)
        }.flow.cachedIn(viewModelScope)
    }

    sealed class UIEvent {
        object Loading : UIEvent()
        data class Success(val artists: List<com.chnouman.lastfmapidemo.data.local.entities.Artist>?) :
            UIEvent()

        data class Error(val message: String?) : UIEvent()
    }
}