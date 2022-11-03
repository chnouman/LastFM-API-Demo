package com.chnouman.lastfmapidemo.domain.usecases

import com.chnouman.lastfmapidemo.BuildConfig
import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository
import kotlinx.coroutines.flow.Flow

class GetAlbumInfo(private val repository: TopAlbumRepository) {

    operator fun invoke(artist: String, album: String): Flow<Resource<MutableList<Track>>> {
        return repository.getAlbumInfo(artist, album, BuildConfig.API_KEY)
    }
}