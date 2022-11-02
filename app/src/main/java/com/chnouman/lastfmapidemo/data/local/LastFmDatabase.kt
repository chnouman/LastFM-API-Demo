package com.chnouman.lastfmapidemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.data.local.dao.AlbumDao
import com.chnouman.lastfmapidemo.data.local.dao.ArtistDao
import com.chnouman.lastfmapidemo.data.local.dao.TrackDao
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.data.local.entities.Artist

@Database(
    entities = [Album::class, Track::class, Artist::class],
    version = 1,
    exportSchema = false
)
abstract class LastFmDatabase : RoomDatabase() {
    abstract fun getAlbumDao(): AlbumDao
    abstract fun getTrackDao(): TrackDao
    abstract fun getArtistDao(): ArtistDao
}