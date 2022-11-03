package com.chnouman.lastfmapidemo.data.repository

import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.data.remote.LastFMApi
import com.chnouman.lastfmapidemo.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class SearchRepositoryImpl(private val api: LastFMApi) : SearchRepository {
    override fun searchArtist(
        query: String,
        apiKey: String
    ): Flow<Resource<MutableList<com.chnouman.lastfmapidemo.data.remote.models.searchartist.Artist>>> =
        flow {
            emit(Resource.Loading())
            try {
                val albumsFromRemote = api.searchArtist(query, apiKey)
                val artists = albumsFromRemote.results.artistmatches.artist
                emit(Resource.Success(artists))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = "Couldn't reach server, check your internet connection.",
                        data = null
                    )
                )
            }
        }
}