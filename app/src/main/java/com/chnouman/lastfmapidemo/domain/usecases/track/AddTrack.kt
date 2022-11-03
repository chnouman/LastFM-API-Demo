package com.chnouman.lastfmapidemo.domain.usecases.track

import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository


class AddTrack(private val repository: TopAlbumRepository) {
    suspend operator fun invoke(tracks: MutableList<Track>) {
        repository.addTracks(tracks)
    }
}