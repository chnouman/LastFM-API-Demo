package com.chnouman.lastfmapidemo.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.domain.usecases.album.DeleteAlbum
import com.chnouman.lastfmapidemo.domain.usecases.album.GetLocalAlbums
import com.chnouman.lastfmapidemo.domain.usecases.artist.DeleteArtist
import com.chnouman.lastfmapidemo.domain.usecases.track.DeleteTracks
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLocalAlbums: GetLocalAlbums,
    val deleteTracks: DeleteTracks,
    val deleteAlbumUseCase: DeleteAlbum,
    val deleteArtist: DeleteArtist
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

    fun deleteAlbum(album: Album) {
        viewModelScope.launch(Dispatchers.IO) {
            val job1 = launch {
                deleteTracks(album.name)
            }
            val job2 = launch {
                deleteAlbumUseCase(album)
                deleteArtist(album.artistName)
            }
            job1.join()
            job2.join()
        }
    }
}
