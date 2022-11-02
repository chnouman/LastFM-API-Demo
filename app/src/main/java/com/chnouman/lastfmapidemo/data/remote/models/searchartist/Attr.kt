package com.chnouman.lastfmapidemo.data.remote.models.searchartist

import com.google.gson.annotations.SerializedName

data class Attr(
    @SerializedName("for")
    val forValue: String
)