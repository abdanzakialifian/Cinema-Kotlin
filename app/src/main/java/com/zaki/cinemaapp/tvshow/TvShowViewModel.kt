package com.zaki.cinemaapp.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zaki.cinemaapp.core.domain.model.TvShow
import com.zaki.cinemaapp.core.domain.usecase.ICinemaUseCase
import com.zaki.cinemaapp.core.vo.Resource

class TvShowViewModel(private val cinemaUseCase: ICinemaUseCase) : ViewModel() {
    private var data: LiveData<Resource<List<TvShow>>> = MutableLiveData()
    fun getTvShow(): LiveData<Resource<List<TvShow>>> {
        if (data.value == null) {
            data = cinemaUseCase.getListTvShow().asLiveData()
        }
        return data
    }
}