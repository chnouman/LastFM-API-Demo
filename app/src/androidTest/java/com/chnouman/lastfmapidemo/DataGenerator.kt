package com.chnouman.lastfmapidemo

import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.data.local.entities.Artist
import kotlin.random.Random

object DataGenerator {
    fun getAlbum(name: String): Album {
        return Album(
            name,
            0,
            "https://lastfm.com",
            "https://lastfm.com",
            "ABC " + kotlin.random.Random.nextInt(),
            false
        )
    }

    fun getArtist(name: String): Artist {
        return Artist(name, "https://lastfm.com")
    }

    fun getTrack(name: String): Track {
        return Track(name,0,"https://lastfm.com","albumName")
    }
}