package com.chnouman.lastfmapidemo.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.chnouman.lastfmapidemo.data.local.entities.Album

@Dao
interface AlbumDao {
    @Insert(onConflict = IGNORE)
    fun insert(wordInfoDto: Album)

    @Insert(onConflict = IGNORE)
    fun insertAll(wordInfoDto: List<Album>)

    @Query("DELETE FROM albums_table WHERE name = :name")
    fun delete(name: String)

    @Query("Select * from albums_table")
    fun getAll(): List<Album>

    @Query("SELECT EXISTS (Select * from albums_table Where name = :name)")
    fun isExist(name: String): Boolean

    @Query("SELECT * FROM albums_table ORDER BY name ASC ")
    fun getAlbumsList(): PagingSource<Int, Album>
}