package com.zaki.cinemaapp.detail

import androidx.lifecycle.*
import com.zaki.cinemaapp.core.domain.model.TvShow
import com.zaki.cinemaapp.core.domain.usecase.ICinemaUseCase
import kotlinx.coroutines.launch

class DetailTvShowViewModel(private val cinemaUseCase: ICinemaUseCase) : ViewModel() {
    fun setFavoriteTvShow(tvShow: TvShow, newState: Boolean) {
        viewModelScope.launch {
            cinemaUseCase.setFavoriteTvShow(tvShow, newState)
        }
    }
}