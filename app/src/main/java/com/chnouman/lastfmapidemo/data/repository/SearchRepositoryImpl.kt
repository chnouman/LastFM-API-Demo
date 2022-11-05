package com.chnouman.lastfmapidemo.data.repository

import com.chnouman.lastfmapidemo.data.remote.LastFMApi
import com.chnouman.lastfmapidemo.data.remote.models.ArtistsSearchResponse
import com.chnouman.lastfmapidemo.domain.repository.SearchRepository

class SearchRepositoryImpl(private val api: LastFMApi) : SearchRepository {
    override suspend fun searchArtistPaged(
        artistQuery: String,
        apiKey: String,
        page: Int,
        limit: Int
    ): ArtistsSearchResponse {
        return api.searchArtistPaged(artistQuery, apiKey, page, limit)
    }
}
