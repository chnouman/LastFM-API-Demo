package com.chnouman.lastfmapidemo.domain.repository

import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.remote.models.searchartist.Artist
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchArtist(query: String, apiKey: String): Flow<Resource<MutableList<Artist>>>
}