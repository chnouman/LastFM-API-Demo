package com.chnouman.lastfmapidemo.data.remote.models

import com.google.gson.annotations.SerializedName

data class ArtistsSearchResponse(
    val results: Results?
)

data class ArtistMatches(
    val artist: List<Artist>?
)


data class Artist(
    val image: List<Image>?,
    val name: String?,
    val url: String?,
    val listeners: String?
)


data class OpensearchQuery(
    @SerializedName("#text")
    val text: String?,
    val role: String?,
    val searchTerms: String?,
    val startPage: String?
)

data class Results(
    @SerializedName("artistmatches")
    val artistmatches: ArtistMatches?,
    @SerializedName("opensearch:Query")
    val opensearch_Query: OpensearchQuery?,
    @SerializedName("opensearch:itemsPerPage")
    val opensearch_itemsPerPage: String?,
    @SerializedName("opensearch:startIndex")
    val opensearch_startIndex: String?,
    @SerializedName("opensearch:totalResults")
    val opensearch_totalResults: String?
)