package com.zaki.cinemaapp.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tbTvShow")
@Parcelize
data class DataTvShow(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "tvShowId")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "imagePath")
    var imagePath: String,

    @ColumnInfo(name = "release")
    var release: String,

    @ColumnInfo(name = "score")
    var score: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "imageBackground")
    var imageBackground: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
) : Parcelable
