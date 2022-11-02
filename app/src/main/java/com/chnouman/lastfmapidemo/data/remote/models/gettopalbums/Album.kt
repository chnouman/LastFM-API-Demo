package com.chnouman.lastfmapidemo.data.remote.models.gettopalbums

import com.chnouman.lastfmapidemo.data.remote.models.searchartist.Artist
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("image")
    val image: List<Image>,
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("playcount")
    val playcount: Int,
    @SerializedName("url")
    val url: String
)