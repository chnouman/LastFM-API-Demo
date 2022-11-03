package com.chnouman.lastfmapidemo.domain.repository

import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.data.local.entities.Track
import kotlinx.coroutines.flow.Flow

interface TopAlbumRepository {
    fun getTopAlbums(query: String, apiKey: String): Flow<Resource<MutableList<Album>>>
    fun getAlbumInfo(artist: String, album: String, apiKey: String): Flow<Resource<Track>>
    fun compareLocalAlbums(albums: MutableList<Album>): MutableList<Album>
    fun getLocalTracks(albumName: String): Flow<Resource<MutableList<Track>>>
    fun addAlbumDto(albumsDto: Album)
    fun addTracks(tracks: MutableList<Track>)
    fun addArtist(artist: com.chnouman.lastfmapidemo.data.local.entities.Artist)
    fun deleteAlbum(albumsDto: Album)
    fun deleteTracks(albumsName: String)
    fun deleteArtist(artist: String)
}