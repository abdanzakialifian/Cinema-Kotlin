package com.zaki.cinemaapp.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zaki.cinemaapp.core.domain.model.Movie
import com.zaki.cinemaapp.core.domain.usecase.ICinemaUseCase
import com.zaki.cinemaapp.core.vo.Resource

class MovieViewModel(private val cinemaUseCase: ICinemaUseCase) : ViewModel() {
    private var data: LiveData<Resource<List<Movie>>> = MutableLiveData()
    fun getMovie(): LiveData<Resource<List<Movie>>> {
        if (data.value == null) {
            data = cinemaUseCase.getListMovie().asLiveData()
        }
        return data
    }
}