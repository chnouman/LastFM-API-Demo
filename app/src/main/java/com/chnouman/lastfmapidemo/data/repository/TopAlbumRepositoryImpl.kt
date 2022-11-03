package com.chnouman.lastfmapidemo.data.repository

import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.data.local.entities.Artist
import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository
import kotlinx.coroutines.flow.Flow

class TopAlbumRepositoryImpl : TopAlbumRepository {
    override fun getTopAlbums(query: String, apiKey: String): Flow<Resource<MutableList<Album>>> {
        TODO("Not yet implemented")
    }

    override fun getAlbumInfo(
        artist: String,
        album: String,
        apiKey: String
    ): Flow<Resource<MutableList<Track>>> {
        TODO("Not yet implemented")
    }

    override fun compareLocalAlbums(albums: MutableList<Album>): MutableList<Album> {
        TODO("Not yet implemented")
    }

    override fun getLocalTracks(albumName: String): Flow<Resource<MutableList<Track>>> {
        TODO("Not yet implemented")
    }

    override fun addAlbumDto(albumsDto: Album) {
        TODO("Not yet implemented")
    }

    override fun addTracks(tracks: MutableList<Track>) {
        TODO("Not yet implemented")
    }

    override fun addArtist(artist: Artist) {
        TODO("Not yet implemented")
    }

    override fun deleteAlbum(albumsDto: Album) {
        TODO("Not yet implemented")
    }

    override fun deleteTracks(albumsName: String) {
        TODO("Not yet implemented")
    }

    override fun deleteArtist(artist: String) {
        TODO("Not yet implemented")
    }
}