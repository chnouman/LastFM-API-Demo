package com.chnouman.lastfmapidemo.domain.usecases

import com.chnouman.lastfmapidemo.BuildConfig
import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTopAlbums(private val repository: TopAlbumRepository) {

    suspend operator fun invoke(word: String): Flow<Resource<MutableList<Album>>> {
        return if (word.isEmpty()) {
            flow { }
        } else {
            return repository.getTopAlbums(word, BuildConfig.API_KEY)
        }
    }
}