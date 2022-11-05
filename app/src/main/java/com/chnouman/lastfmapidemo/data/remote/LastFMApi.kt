package com.chnouman.lastfmapidemo.data.remote

import com.chnouman.lastfmapidemo.data.remote.models.ArtistsSearchResponse
import com.chnouman.lastfmapidemo.data.remote.models.GetAlbumInfo
import com.chnouman.lastfmapidemo.data.remote.models.GetTopAlbums
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMApi {
    @GET("?method=artist.search&format=json")
    suspend fun searchArtist(
        @Query("artist") artistQuery: String,
        @Query("api_key") apiKey: String
    ): ArtistsSearchResponse

    @GET("?method=artist.search&format=json")
    suspend fun searchArtistPaged(
        @Query("artist") artistQuery: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ArtistsSearchResponse

    @GET("?method=artist.gettopalbums&format=json&limit=10")
    suspend fun getTopAlbums(
        @Query("artist") artistQuery: String,
        @Query("api_key") apiKey: String
    ): GetTopAlbums

    @GET("?method=album.getinfo&format=json")
    suspend fun getAlbumInfo(
        @Query("artist") artistQuery: String,
        @Query("album") album: String,
        @Query("api_key") apiKey: String
    ): GetAlbumInfo
}
