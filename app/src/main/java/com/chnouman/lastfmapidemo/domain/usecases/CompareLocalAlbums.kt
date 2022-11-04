package com.chnouman.lastfmapidemo.domain.usecases

import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository


class CompareLocalAlbums(private val repository: TopAlbumRepository) {
    suspend operator fun invoke(albums: List<Album>): List<Album> {
        return repository.compareLocalAlbums(albums)
    }
}