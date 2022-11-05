package com.chnouman.lastfmapidemo.domain.usecases.artist

import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository

class DeleteArtist(private val repository: TopAlbumRepository) {
    suspend operator fun invoke(albumName: String) {
        return repository.deleteArtist(albumName)
    }
}
