package com.zaki.cinemaapp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var title: String,
    var score: String,
    var imagePath: String,
    var release: String,
    var overview: String,
    var imageBackground: String,
    var favorite: Boolean = false
) : Parcelable
