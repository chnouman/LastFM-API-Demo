package com.chnouman.cleancode.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks_table")
data class Track(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val duration: Int,
    val url: String,
    val albumName: String
)