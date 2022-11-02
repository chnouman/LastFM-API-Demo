package com.chnouman.lastfmapidemo.data.remote.models.gettopalbums

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text")
    val text: String,
    val size: String
)