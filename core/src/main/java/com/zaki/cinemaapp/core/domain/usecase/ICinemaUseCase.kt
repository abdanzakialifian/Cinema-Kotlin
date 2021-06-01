package com.zaki.cinemaapp.core.domain.usecase

import com.zaki.cinemaapp.core.domain.model.Movie
import com.zaki.cinemaapp.core.domain.model.TvShow
import com.zaki.cinemaapp.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface ICinemaUseCase {
    fun getListMovie(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovie(): Flow<List<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)

    fun getListTvShow(): Flow<Resource<List<TvShow>>>

    fun getFavoriteTvShow(): Flow<List<TvShow>>

    fun setFavoriteTvShow(tvShow: TvShow, state: Boolean)
}