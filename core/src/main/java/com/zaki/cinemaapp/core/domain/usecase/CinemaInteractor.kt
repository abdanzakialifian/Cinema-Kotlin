package com.zaki.cinemaapp.core.domain.usecase

import com.zaki.cinemaapp.core.domain.model.Movie
import com.zaki.cinemaapp.core.domain.model.TvShow
import com.zaki.cinemaapp.core.domain.repository.ICinemaRepository

class CinemaInteractor(private val cinemaRepository: ICinemaRepository) : ICinemaUseCase {
    override fun getListMovie() = cinemaRepository.getListMovie()

    override fun getFavoriteMovie() = cinemaRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        cinemaRepository.setFavoriteMovie(movie, state)

    override fun getListTvShow() = cinemaRepository.getListTvShow()

    override fun getFavoriteTvShow() = cinemaRepository.getFavoriteTvShow()

    override fun setFavoriteTvShow(tvShow: TvShow, state: Boolean) =
        cinemaRepository.setFavoriteTvShow(tvShow, state)
}