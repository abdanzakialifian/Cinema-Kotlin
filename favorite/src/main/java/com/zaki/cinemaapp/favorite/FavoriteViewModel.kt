package com.zaki.cinemaapp.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zaki.cinemaapp.core.domain.model.Movie
import com.zaki.cinemaapp.core.domain.model.TvShow
import com.zaki.cinemaapp.core.domain.usecase.ICinemaUseCase

class FavoriteViewModel(private val cinemaUseCase: ICinemaUseCase) : ViewModel() {
    fun getFavoriteMovie(): LiveData<List<Movie>> = cinemaUseCase.getFavoriteMovie().asLiveData()

    fun getFavoriteTvShow(): LiveData<List<TvShow>> = cinemaUseCase.getFavoriteTvShow().asLiveData()

    fun setMovie(dataMovie: Movie) {
        val newState = !dataMovie.favorite
        cinemaUseCase.setFavoriteMovie(dataMovie, newState)
    }

    fun setTvShow(dataTvShow: TvShow) {
        val newState = !dataTvShow.favorite
        cinemaUseCase.setFavoriteTvShow(dataTvShow, newState)
    }
}