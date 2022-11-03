package com.chnouman.lastfmapidemo.data.local.entities

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "albums_table")
@Parcelize
data class  Album @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("name")
    val name: String,
    @SerializedName("playcount")
    val playCount: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("artistName")
    val artistName: String,
    @Ignore
    var isDownloaded:Boolean = false
) : Parcelable
