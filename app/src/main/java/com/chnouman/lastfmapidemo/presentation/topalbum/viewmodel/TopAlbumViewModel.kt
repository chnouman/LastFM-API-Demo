package com.chnouman.lastfmapidemo.presentation.topalbum.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chnouman.lastfmapidemo.domain.usecases.artist.DeleteArtist
import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.data.local.entities.Artist
import com.chnouman.lastfmapidemo.domain.usecases.AddAlbum
import com.chnouman.lastfmapidemo.domain.usecases.CompareLocalAlbums
import com.chnouman.lastfmapidemo.domain.usecases.DeleteAlbum
import com.chnouman.lastfmapidemo.domain.usecases.GetAlbumInfo
import com.chnouman.lastfmapidemo.domain.usecases.artist.AddArtist
import com.chnouman.lastfmapidemo.domain.usecases.track.AddTrack
import com.chnouman.lastfmapidemo.domain.usecases.track.DeleteTracks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopAlbumViewModel @Inject constructor(
    val addTrack: AddTrack,
    val getAlbumInfo: GetAlbumInfo,
    val getTopAlbumsUseCase: com.chnouman.lastfmapidemo.domain.usecases.GetTopAlbums,
    val compareLocalAlbums: CompareLocalAlbums,
    val deleteAlbumUseCase: DeleteAlbum,
    val addAlbumUseCase: AddAlbum,
    val addArtist: AddArtist,
    val deleteTracks: DeleteTracks,
    val deleteArtist: DeleteArtist,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    var currentItems = mutableListOf<Album>()

    fun getTopAlbums(artist: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getTopAlbumsUseCase(artist)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                val compareLocalAlbums1 = compareLocalAlbums(it)
                                currentItems = compareLocalAlbums1
                                _eventFlow.emit(UIEvent.Success(compareLocalAlbums1))
                                Log.d("MainViewModelTest", "search: success ${result.data.size}")
                            }
                        }
                        is Resource.Error -> {
                            result.message?.let { UIEvent.Error(it) }?.let {
                                Log.d("MainViewModelTest", "search: Error ${it.message}")
                                _eventFlow.emit(
                                    it
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _eventFlow.emit(UIEvent.Loading)
                            Log.d("MainViewModelTest", "search: Loading")
                        }
                    }
                }.launchIn(this)
        }
    }

    fun saveAlbum(list: MutableList<Album>, albumsDto: Album, artist: Artist) {
        viewModelScope.launch(Dispatchers.IO) {
            //load the album detail
            val tracks = getAlbumInfo(artist.name, albumsDto.name)
            val job1 = launch { addArtist(artist) }
            val job2 = launch { addAlbumUseCase(albumsDto) }
            val job3 = launch {
                tracks.collectLatest {
                    it.data?.let { it1 -> addTrack(it1) }
                }
            }
            job1.join()
            job2.join()
            job3.join()
            Log.d("saveAlbum", "saveAlbum: ")
            //TODO fix this updation part
            _eventFlow.emit(UIEvent.ItemSaved(albumsDto))

        }
    }

    fun deleteAlbum(albumsDto: Album) {
        viewModelScope.launch(Dispatchers.IO) {
            launch { deleteTracks(albumsDto.name) }
            launch {
                deleteAlbumUseCase(albumsDto)
                deleteArtist(albumsDto.artistName)
            }
        }
    }

    sealed class UIEvent {
        object Loading : UIEvent()
        data class Success(val artists: MutableList<Album>?) : UIEvent()
        data class ItemSaved(val artists: Album) : UIEvent()
        data class Error(val message: String) : UIEvent()
    }
}