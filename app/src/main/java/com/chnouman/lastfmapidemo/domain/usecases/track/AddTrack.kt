package com.chnouman.lastfmapidemo.domain.usecases.track

import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.data.remote.models.getalbuminfo.Tracks
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository


class AddTrack(private val repository: TopAlbumRepository) {
    operator fun invoke(tracks: Tracks, albumName: String) {
        val tracksDto =
            tracks.tracks.map { Track(it.name, it.duration, it.url, albumName) }.toMutableList()
        repository.addTracks(tracksDto)
    }
}