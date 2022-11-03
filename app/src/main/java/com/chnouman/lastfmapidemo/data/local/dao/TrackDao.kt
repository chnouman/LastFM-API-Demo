package com.chnouman.lastfmapidemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.chnouman.lastfmapidemo.data.local.entities.Track

@Dao
interface TrackDao {
    @Insert(onConflict = IGNORE)
    fun insert(track: Track)

    @Insert(onConflict = IGNORE)
    fun insertAll(tracks: List<Track>)

    @Query("DELETE FROM tracks_table WHERE albumName = :albumName")
    fun delete(albumName: String)

    @Query("Select * from tracks_table Where albumName = :albumName")
    fun getAll(albumName:String): MutableList<Track>
}