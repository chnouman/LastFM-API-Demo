package com.chnouman.lastfmapidemo.di

import com.chnouman.lastfmapidemo.data.local.dao.AlbumDao
import com.chnouman.lastfmapidemo.data.remote.LastFMApi
import com.chnouman.lastfmapidemo.data.repository.MainRepositoryImpl
import com.chnouman.lastfmapidemo.data.repository.SearchRepositoryImpl
import com.chnouman.lastfmapidemo.data.repository.TopAlbumRepositoryImpl
import com.chnouman.lastfmapidemo.domain.repository.MainRepository
import com.chnouman.lastfmapidemo.domain.repository.SearchRepository
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMainRepository(
        albumsDao: AlbumDao
    ): MainRepository {
        return MainRepositoryImpl(
            albumsDao
        )
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        lastFMApi: LastFMApi
    ): SearchRepository {
        return SearchRepositoryImpl(
            lastFMApi
        )
    }

    @Provides
    @Singleton
    fun provideTopAlbumRepository(): TopAlbumRepository {
        return TopAlbumRepositoryImpl()
    }
}