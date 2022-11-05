package com.chnouman.lastfmapidemo.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "albums_table")
@Parcelize
data class Album @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("name")
    var name: String,
    @SerializedName("playcount")
    var playCount: Int,
    @SerializedName("url")
    var url: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("artistName")
    var artistName: String,
    @Ignore
    var isDownloaded: Boolean = false
) : Parcelable
