package com.chnouman.lastfmapidemo.data.remote.models.getalbuminfo

class Tracks(vararg track: Track) {
    var tracks: MutableList<Track>

    init {
        tracks = track.toMutableList()
    }
}