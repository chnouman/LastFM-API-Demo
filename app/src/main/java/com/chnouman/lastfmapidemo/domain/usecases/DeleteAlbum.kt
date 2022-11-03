package com.chnouman.lastfmapidemo.domain.usecases

import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository


class DeleteAlbum(private val repository: TopAlbumRepository) {
    operator fun invoke(albumsDto: Album) {
        return repository.deleteAlbum(albumsDto)
    }
}