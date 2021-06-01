package com.zaki.cinemaapp.detail

import androidx.lifecycle.*
import com.zaki.cinemaapp.core.domain.model.Movie
import com.zaki.cinemaapp.core.domain.usecase.ICinemaUseCase
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val cinemaUseCase: ICinemaUseCase) : ViewModel() {
    fun setFavoriteMovie(movie: Movie, newState: Boolean) {
        viewModelScope.launch {
            cinemaUseCase.setFavoriteMovie(movie, newState)
        }
    }
}