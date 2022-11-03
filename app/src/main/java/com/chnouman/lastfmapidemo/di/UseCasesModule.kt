package com.chnouman.lastfmapidemo.di

import com.chnouman.lastfmapidemo.domain.repository.MainRepository
import com.chnouman.lastfmapidemo.domain.usecases.GetLocalAlbums
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
    //End region UseCase dependencies
}