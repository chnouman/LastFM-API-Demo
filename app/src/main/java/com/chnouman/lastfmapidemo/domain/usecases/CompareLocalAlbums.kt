package com.chnouman.lastfmapidemo.domain.usecases

import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository


class CompareLocalAlbums(private val repository: TopAlbumRepository) {
    operator fun invoke(albums: MutableList<Album>): MutableList<Album> {
        return repository.compareLocalAlbums(albums)
    }
}