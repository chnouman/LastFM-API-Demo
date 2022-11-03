package com.chnouman.lastfmapidemo.domain.repository

import androidx.paging.PagingSource
import com.chnouman.lastfmapidemo.data.local.entities.Album

interface MainRepository {
    fun getLocalAlbums(): PagingSource<Int, Album>
}