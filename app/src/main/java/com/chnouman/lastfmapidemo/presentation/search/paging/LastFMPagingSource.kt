package com.chnouman.lastfmapidemo.presentation.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chnouman.lastfmapidemo.core.util.extensions.empty
import com.chnouman.lastfmapidemo.data.local.entities.Artist
import com.chnouman.lastfmapidemo.domain.usecases.artist.SearchArtist

class LastFMPagingSource(
    private val artistQuery: String,
    private val searchArtist: SearchArtist
) : PagingSource<Int, Artist>() {
    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(anchorPageIndex - 1)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        return try {
            val currentPage = params.key ?: 1
            val response = searchArtist(artistQuery, currentPage, 10)
            val data = response.results?.artistmatches?.artist
            val localArtists = data?.map {
                Artist(
                    it.name ?: String.empty,
                    it.url ?: String.empty,
                    it.listeners ?: String.empty,
                    it.image?.lastOrNull()?.text ?: String.empty
                )
            }
            if (localArtists?.isEmpty() == true) {
                LoadResult.Error(Throwable("No match found"))
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
