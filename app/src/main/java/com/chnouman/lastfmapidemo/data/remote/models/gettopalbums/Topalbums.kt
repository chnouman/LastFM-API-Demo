package com.chnouman.lastfmapidemo.data.remote.models.gettopalbums

import com.google.gson.annotations.SerializedName

data class Topalbums(
    @SerializedName("@attr")
    val attr: Attr,
    @SerializedName("album")
    val album: List<Album>
)