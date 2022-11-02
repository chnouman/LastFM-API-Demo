package com.chnouman.lastfmapidemo.data.remote.models.searchartist

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("@attr")
    val attr: Attr,
    @SerializedName("artistmatches")
    val artistmatches: Artistmatches,
    @SerializedName("opensearch:Query")
    val opensearch_Query: OpensearchQuery,
    @SerializedName("opensearch:itemsPerPage")
    val opensearch_itemsPerPage: String,
    @SerializedName("opensearch:startIndex")
    val opensearch_startIndex: String,
    @SerializedName("opensearch:totalResults")
    val opensearch_totalResults: String
)