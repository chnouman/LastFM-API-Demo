package com.chnouman.lastfmapidemo.domain.usecases.album

import com.chnouman.lastfmapidemo.BuildConfig
import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository
import kotlinx.coroutines.flow.Flow

class GetAlbumInfo(private val repository: TopAlbumRepository) {

    suspend operator fun invoke(artist: String, album: String): Flow<Resource<List<Track>>> {
        return repository.getAlbumInfo(artist, album, BuildConfig.API_KEY)
    }
}
