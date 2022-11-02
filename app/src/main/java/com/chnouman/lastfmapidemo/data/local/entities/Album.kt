package com.chnouman.lastfmapidemo.data.local.entities

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(tableName = "albums_table")
@Parcelize
data class  Album @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val playCount: Int,
    val url: String,
    val image: String,
    val artistName: String,
    @Ignore
    var isDownloaded:Boolean = false
) : Parcelable
