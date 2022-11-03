package com.chnouman.lastfmapidemo.di

import com.chnouman.lastfmapidemo.domain.usecases.track.AddTrack
import com.chnouman.lastfmapidemo.domain.usecases.track.DeleteTracks
import com.chnouman.lastfmapidemo.domain.repository.MainRepository
import com.chnouman.lastfmapidemo.domain.repository.SearchRepository
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository
import com.chnouman.lastfmapidemo.domain.usecases.GetAlbumInfo
import com.chnouman.lastfmapidemo.domain.usecases.GetLocalAlbums
import com.chnouman.lastfmapidemo.domain.usecases.SearchArtist
import com.chnouman.lastfmapidemo.domain.usecases.track.GetLocalTracks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    //Start Region UseCase dependencies

    @Provides
    @Singleton
    fun provideGetAlbums(repository: MainRepository): GetLocalAlbums {
        return GetLocalAlbums(repository)
    }

    @Provides
    @Singleton
    fun provideSearchArtistUseCase(repository: SearchRepository): SearchArtist {
        return SearchArtist(repository)
    }


    @Provides
    @Singleton
    fun provideGetLocalTracks(repository: TopAlbumRepository): GetLocalTracks {
        return GetLocalTracks(repository)
    }

    @Provides
    @Singleton
    fun provideAddTrack(repository: TopAlbumRepository): AddTrack {
        return AddTrack(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTracks(repository: TopAlbumRepository): DeleteTracks {
        return DeleteTracks(repository)
    }

    @Provides
    @Singleton
    fun provideGetAlbumInfo(repository: TopAlbumRepository): GetAlbumInfo {
        return GetAlbumInfo(repository)
    }

    //End region UseCase dependencies
}