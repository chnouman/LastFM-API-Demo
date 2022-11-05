package com.chnouman.lastfmapidemo.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "artist_table")
data class Artist(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val url: String,
    val listeners: String,
    val image: String
) : Parcelable