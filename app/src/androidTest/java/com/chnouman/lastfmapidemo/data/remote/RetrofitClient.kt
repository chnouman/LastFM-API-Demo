package com.chnouman.lastfmapidemo.data.remote

import com.chnouman.lastfmapidemo.data.remote.deserializer.TracksDeserializer
import com.chnouman.lastfmapidemo.data.remote.models.Tracks
import com.chnouman.lastfmapidemo.di.NetworkModule
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    val retrofit: Retrofit by lazy {
        val gson = GsonBuilder().registerTypeAdapter(Tracks::class.java, TracksDeserializer())
            .create()
        Retrofit.Builder()
            .baseUrl(NetworkModule.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(gson))
            .build()
    }
}