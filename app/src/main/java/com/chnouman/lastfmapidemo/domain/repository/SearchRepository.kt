package com.chnouman.lastfmapidemo.domain.repository

import com.chnouman.lastfmapidemo.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchArtist(query: String, apiKey: String): Flow<Resource<List<com.chnouman.lastfmapidemo.data.local.entities.Artist>>>
}