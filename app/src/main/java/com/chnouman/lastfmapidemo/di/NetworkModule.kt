package com.chnouman.lastfmapidemo.di

import com.chnouman.lastfmapidemo.data.remote.LastFMApi
import com.chnouman.lastfmapidemo.data.remote.deserializer.TracksDeserializer
import com.chnouman.lastfmapidemo.data.remote.models.Tracks
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLastFMApi(gson: Gson, okHttpClient: OkHttpClient): LastFMApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
            .create(LastFMApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().registerTypeAdapter(Tracks::class.java, TracksDeserializer())
            .create()
    }

    const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"
}
