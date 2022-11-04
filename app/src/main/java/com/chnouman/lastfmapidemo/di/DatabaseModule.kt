package com.chnouman.lastfmapidemo.di

import android.app.Application
import androidx.room.Room
import com.chnouman.lastfmapidemo.core.util.Constants
import com.chnouman.lastfmapidemo.data.local.LastFmDatabase
import com.chnouman.lastfmapidemo.data.local.dao.AlbumDao
import com.chnouman.lastfmapidemo.data.local.dao.ArtistDao
import com.chnouman.lastfmapidemo.data.local.dao.TrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAlbumDao(lastFmDatabase: LastFmDatabase): AlbumDao {
        return lastFmDatabase.albumDao
    }

    @Provides
    @Singleton
    fun provideTrackDao(wordDatabase: LastFmDatabase): TrackDao {
        return wordDatabase.trackDao
    }

    @Provides
    @Singleton
    fun provideArtistDao(wordDatabase: LastFmDatabase): ArtistDao {
        return wordDatabase.artisDao
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): LastFmDatabase {
        return Room.databaseBuilder(application, LastFmDatabase::class.java, Constants.DB_NAME)
            .build()
    }

}