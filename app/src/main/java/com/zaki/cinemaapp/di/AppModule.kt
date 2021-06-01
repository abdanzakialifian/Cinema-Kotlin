package com.zaki.cinemaapp.di

import com.zaki.cinemaapp.core.domain.usecase.CinemaInteractor
import com.zaki.cinemaapp.core.domain.usecase.ICinemaUseCase
import com.zaki.cinemaapp.detail.DetailMovieViewModel
import com.zaki.cinemaapp.detail.DetailTvShowViewModel
import com.zaki.cinemaapp.movie.MovieViewModel
import com.zaki.cinemaapp.tvshow.TvShowViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<ICinemaUseCase> { CinemaInteractor(get()) }
}

val viewModelModule = module {
    viewModel { DetailMovieViewModel(get()) }
    viewModel { DetailTvShowViewModel(get()) }
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
}