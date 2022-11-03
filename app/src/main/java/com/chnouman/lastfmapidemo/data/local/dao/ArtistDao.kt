package com.chnouman.lastfmapidemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.chnouman.lastfmapidemo.data.local.entities.Artist

@Dao
interface ArtistDao {
    @Insert(onConflict = IGNORE)
    fun insert(artist: Artist)

    @Insert(onConflict = IGNORE)
    fun insertAll(artists: List<Artist>)

    @Query("DELETE FROM artist_table WHERE name = :name")
    fun delete(name: String)

    @Query("Select * from artist_table Where name = :name")
    fun getArtist(name: String): Artist
}