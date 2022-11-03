package com.chnouman.lastfmapidemo.domain.usecases

import com.chnouman.lastfmapidemo.BuildConfig
import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.local.entities.Artist
import com.chnouman.lastfmapidemo.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchArtist(private val repository: SearchRepository) {

    operator fun invoke(word: String): Flow<Resource<MutableList<Artist>>> {
        return if (word.isEmpty()) {
            flow { }
        } else {
            return repository.searchArtist(word, BuildConfig.API_KEY)
        }
    }
}