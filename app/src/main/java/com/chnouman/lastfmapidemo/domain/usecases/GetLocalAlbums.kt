package com.chnouman.lastfmapidemo.domain.usecases

import androidx.paging.PagingSource
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.domain.repository.MainRepository

class GetLocalAlbums(private val repository: MainRepository) {
    operator fun invoke(): PagingSource<Int, Album> {
        return repository.getLocalAlbums()
    }
}
