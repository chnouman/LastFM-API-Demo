package com.chnouman.lastfmapidemo.di

import com.chnouman.lastfmapidemo.data.local.dao.AlbumDao
import com.chnouman.lastfmapidemo.data.repository.MainRepositoryImpl
import com.chnouman.lastfmapidemo.domain.repository.MainRepository
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
}