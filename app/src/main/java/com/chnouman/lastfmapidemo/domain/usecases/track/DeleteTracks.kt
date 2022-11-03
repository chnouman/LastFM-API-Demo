package com.chnouman.lastfmapidemo.domain.usecases.track

import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository


class DeleteTracks(private val repository: TopAlbumRepository) {
    suspend operator fun invoke(albumName: String) {
        return repository.deleteTracks(albumName)
    }
}