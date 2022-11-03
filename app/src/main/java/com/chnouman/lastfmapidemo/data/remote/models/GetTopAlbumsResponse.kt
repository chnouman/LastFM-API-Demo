package com.chnouman.lastfmapidemo.data.remote.models

import com.google.gson.annotations.SerializedName

data class GetTopAlbums(
    val topalbums: TopAlbums?
)
data class Attr(
    val artist: String?,
    val page: String,
    val perPage: String?,
    val total: String?,
    val totalPages: String?
)
data class Album(
    @SerializedName("artist")
    val artist: Artist?,
    @SerializedName("image")
    val image: List<Image>?,
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("playcount")
    val playcount: Int?,
    @SerializedName("url")
    val url: String?
)
data class Image(
    @SerializedName("#text")
    val text: String?,
    val size: String?
)
data class TopAlbums(
    @SerializedName("@attr")
    val attr: Attr?,
    @SerializedName("album")
    val album: List<Album>?
)