package com.chnouman.lastfmapidemo.data.remote.models

data class GetAlbumInfo(
    val album: AlbumObject?
)
data class Track(
    val duration: Int?,
    val name: String?,
    val url: String?
)
class Tracks(vararg track: Track) {
    var tracks: List<Track>?

    init {
        tracks = track.toList()
    }
}
data class AlbumObject(
    val tracks: Tracks?
)
