package com.chnouman.lastfmapidemo.data.repository

import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.data.local.entities.Artist
import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeTopAlbumRepository : TopAlbumRepository {

    private var shouldReturnError = false

    override suspend fun getTopAlbums(query: String, apiKey: String): Flow<Resource<List<Album>>> {
        if (shouldReturnError) return flowOf(Resource.Error("Error"))
        return flow {
            emit(Resource.Loading())
            emit(Resource.Success(listOf(Album("ABC", 200, "", "", "", false))))
        }
    }

    override suspend fun getAlbumInfo(
        artist: String,
        album: String,
        apiKey: String
    ): Flow<Resource<List<Track>>> {
        if (shouldReturnError) return flowOf(Resource.Error("Error"))
        return flow {

            emit(Resource.Success(listOf(Track("ABC", 200, "", album))))
        }
    }

    override suspend fun compareLocalAlbums(albums: List<Album>): List<Album> {
        if (shouldReturnError) return albums
        albums.forEachIndexed { index, album -> if (index % 2 == 0) album.isDownloaded = true }
        return albums
    }

    override suspend fun getLocalTracks(albumName: String): Flow<Resource<List<Track>>> {
        if (shouldReturnError) return flowOf(Resource.Error("Error"))
        return flowOf(Resource.Success(listOf(Track("ABC", 200, "", albumName))))
    }

    override suspend fun addAlbumDto(albumsDto: Album) {
//assumed as success
    }

    override suspend fun addTracks(tracks: List<Track>) {
        //assumed as success
    }

    override suspend fun addArtist(artist: Artist) {
        //assumed as success
    }

    override suspend fun deleteAlbum(albumsDto: Album) {
        //assumed as success
    }

    override suspend fun deleteTracks(albumsName: String) {
        //assumed as success
    }

    override suspend fun deleteArtist(artist: String) {
        //assumed as success
    }
}