package com.chnouman.lastfmapidemo.domain.usecases.artist

import com.chnouman.lastfmapidemo.data.local.entities.Artist
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository


class AddArtist(private val repository: TopAlbumRepository) {
    operator fun invoke(artist: Artist) {
        repository.addArtist(artist)
    }
}