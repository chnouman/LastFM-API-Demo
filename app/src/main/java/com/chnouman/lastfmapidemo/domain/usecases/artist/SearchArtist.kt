package com.chnouman.lastfmapidemo.domain.usecases.artist

import com.chnouman.lastfmapidemo.BuildConfig
import com.chnouman.lastfmapidemo.data.remote.models.ArtistsSearchResponse
import com.chnouman.lastfmapidemo.domain.repository.SearchRepository

class SearchArtist(private val repository: SearchRepository) {

    suspend operator fun invoke(
        artistQuery: String,
        page: Int,
        limit: Int
    ): ArtistsSearchResponse {
        return repository.searchArtistPaged(artistQuery, BuildConfig.API_KEY, page, limit)
    }
}
