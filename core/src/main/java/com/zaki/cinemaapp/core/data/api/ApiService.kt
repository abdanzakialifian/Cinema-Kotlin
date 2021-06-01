package com.zaki.cinemaapp.core.data.api

import com.zaki.cinemaapp.core.data.source.remote.response.MovieResponse
import com.zaki.cinemaapp.core.data.source.remote.response.ResultsItem
import com.zaki.cinemaapp.core.data.source.remote.response.ResultsShow
import com.zaki.cinemaapp.core.data.source.remote.response.TvShowResponse
import com.zaki.cinemaapp.core.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovie(
        @Query("api_key") apiKey: String = BuildConfig.MOVIE_TOKEN
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.MOVIE_TOKEN
    ): ResultsItem

    @GET("tv/popular")
    suspend fun getTvShow(
        @Query("api_key") apiKey: String = BuildConfig.MOVIE_TOKEN
    ): TvShowResponse

    @GET("tv/{tv_id}")
    suspend fun getDetailTvShow(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = BuildConfig.MOVIE_TOKEN
    ): ResultsShow
}