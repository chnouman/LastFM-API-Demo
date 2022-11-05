package com.chnouman.lastfmapidemo.data.repository

import com.chnouman.lastfmapidemo.core.util.Resource
import com.chnouman.lastfmapidemo.core.util.extensions.empty
import com.chnouman.lastfmapidemo.data.local.entities.Artist
import com.chnouman.lastfmapidemo.data.remote.LastFMApi
import com.chnouman.lastfmapidemo.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class SearchRepositoryImpl(private val api: LastFMApi) : SearchRepository {
    override suspend fun searchArtist(
        query: String,
        apiKey: String
    ): Flow<Resource<List<Artist>>> =
        flow {
            emit(Resource.Loading())
            try {
                val albumsFromRemote = api.searchArtist(query, apiKey)
                val artists = albumsFromRemote.results?.artistmatches?.artist
                val artistsLocal = artists?.map {
                    Artist(
                        it.name ?: String.empty,
                        it.url ?: String.empty,
                        it.listeners ?: String.empty,
                        it.image?.lastOrNull()?.text ?: String.empty
                    )
                }
                if (artistsLocal.isNullOrEmpty()) {
                    emit(Resource.Error("Nothing Found"))
                } else {
                    emit(Resource.Success(artistsLocal))
                }
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