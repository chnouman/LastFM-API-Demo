package com.chnouman.lastfmapidemo.domain.usecases.track

import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository
import kotlinx.coroutines.flow.Flow

class GetLocalTracks(private val repository: TopAlbumRepository) {
    operator fun invoke(albumName: String): Flow<Resource<MutableList<Track>>> {
        return repository.getLocalTracks(albumName)
    }
}