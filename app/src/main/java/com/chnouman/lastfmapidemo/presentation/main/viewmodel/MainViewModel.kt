package com.chnouman.lastfmapidemo.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.chnouman.lastfmapidemo.domain.usecases.album.GetLocalAlbums
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLocalAlbums: GetLocalAlbums
) : ViewModel() {
    val data = Pager(
        PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            initialLoadSize = 5
        )
    ) {
        getLocalAlbums()
    }.flow.flowOn(Dispatchers.IO).cachedIn(viewModelScope)
}
