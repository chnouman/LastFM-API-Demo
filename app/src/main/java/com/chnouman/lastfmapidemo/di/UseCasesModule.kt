package com.chnouman.lastfmapidemo.di

import com.chnouman.lastfmapidemo.domain.repository.MainRepository
import com.chnouman.lastfmapidemo.domain.repository.SearchRepository
import com.chnouman.lastfmapidemo.domain.repository.TopAlbumRepository
import com.chnouman.lastfmapidemo.domain.usecases.album.AddAlbum
import com.chnouman.lastfmapidemo.domain.usecases.album.CompareLocalAlbums
import com.chnouman.lastfmapidemo.domain.usecases.album.DeleteAlbum
import com.chnouman.lastfmapidemo.domain.usecases.album.GetAlbumInfo
import com.chnouman.lastfmapidemo.domain.usecases.album.GetLocalAlbums
import com.chnouman.lastfmapidemo.domain.usecases.album.GetTopAlbums
import com.chnouman.lastfmapidemo.domain.usecases.artist.SearchArtist
import com.chnouman.lastfmapidemo.domain.usecases.artist.AddArtist
import com.chnouman.lastfmapidemo.domain.usecases.artist.DeleteArtist
import com.chnouman.lastfmapidemo.domain.usecases.track.AddTrack
import com.chnouman.lastfmapidemo.domain.usecases.track.DeleteTracks
import com.chnouman.lastfmapidemo.domain.usecases.track.GetLocalTracks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    // Start Region UseCase dependencies
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

    @Provides
    @Singleton
    fun provideAddAlbum(repository: TopAlbumRepository): AddAlbum {
        return AddAlbum(repository)
    }

    @Provides
    @Singleton
    fun provideGetTopAlbums(repository: TopAlbumRepository): GetTopAlbums {
        return GetTopAlbums(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteAlbum(repository: TopAlbumRepository): DeleteAlbum {
        return DeleteAlbum(repository)
    }

    @Provides
    @Singleton
    fun provideAddArtist(repository: TopAlbumRepository): AddArtist {
        return AddArtist(repository)
    }

    @Provides
    @Singleton
    fun provideCompareLocalAlbums(repository: TopAlbumRepository): CompareLocalAlbums {
        return CompareLocalAlbums(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteArtist(repository: TopAlbumRepository): DeleteArtist {
        return DeleteArtist(repository)
    }
    // End region UseCase dependencies
}
