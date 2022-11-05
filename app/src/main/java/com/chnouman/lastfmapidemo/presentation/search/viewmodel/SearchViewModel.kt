package com.chnouman.lastfmapidemo.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chnouman.lastfmapidemo.domain.usecases.artist.SearchArtist
import com.chnouman.lastfmapidemo.presentation.search.paging.LastFMPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArtist: SearchArtist
) : ViewModel() {
    fun searchArtistPaged(artistQuery: String): Flow<PagingData<com.chnouman.lastfmapidemo.data.local.entities.Artist>> {
        return Pager(PagingConfig(pageSize = 1)) {
            LastFMPagingSource(artistQuery, searchArtist)
        }.flow.cachedIn(viewModelScope)
    }
}
