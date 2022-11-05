package com.chnouman.lastfmapidemo.domain.repository

import com.chnouman.lastfmapidemo.data.remote.models.ArtistsSearchResponse

interface SearchRepository {
    suspend fun searchArtistPaged(
        artistQuery: String,
        apiKey: String,
        page: Int,
        limit: Int
    ): ArtistsSearchResponse
}
