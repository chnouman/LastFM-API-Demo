package com.chnouman.lastfmapidemo.presentation.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chnouman.lastfmapidemo.core.util.extensions.empty
import com.chnouman.lastfmapidemo.data.local.entities.Artist
import com.chnouman.lastfmapidemo.data.remote.LastFMApi

class LastFMPagingSource(
    private val artistQuery: String,
    private val apiKey: String,
    private val lastFMApi: LastFMApi
) : PagingSource<Int, Artist>() {
    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        return try {
            val currentPage = params.key ?: 1
            val response = lastFMApi.searchArtistPaged(artistQuery, apiKey, currentPage, 10)
            val data = response.results?.artistmatches?.artist
            val localArtists = data?.map {
                Artist(it.name ?: String.empty, it.url ?: String.empty)
            }
            if (localArtists?.isEmpty() == true) {
                LoadResult.Error(Throwable("No more items"))//TODO fix the empty list case and stop paging
            } else {
                LoadResult.Page(
                    data = localArtists!!,
                    prevKey = if (currentPage == 1) null else -1,
                    nextKey = currentPage.plus(1)
                )
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}