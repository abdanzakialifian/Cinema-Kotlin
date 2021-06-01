package com.zaki.cinemaapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponse(

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val results: List<ResultsShow>
)

data class ResultsShow(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("backdrop_path")
    val backdropPath: String,

    )
