package com.chnouman.lastfmapidemo.domain.usecases.album

import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository

class AddAlbum(private val repository: TopAlbumRepository) {
    suspend operator fun invoke(albumsDto: Album) {
        return repository.addAlbumDto(albumsDto)
    }
}