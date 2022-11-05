package com.chnouman.lastfmapidemo.data.repository

import androidx.paging.PagingSource
import com.chnouman.lastfmapidemo.data.local.dao.AlbumDao
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.domain.repository.MainRepository

class MainRepositoryImpl(
    private val albumsDao: AlbumDao
) : MainRepository {
    override fun getLocalAlbums(): PagingSource<Int, Album> = albumsDao.getAlbumsList()
}
