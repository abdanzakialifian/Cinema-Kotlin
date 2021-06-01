package com.zaki.cinemaapp.core.data.source.local

import com.zaki.cinemaapp.core.data.source.local.entity.DataMovie
import com.zaki.cinemaapp.core.data.source.local.entity.DataTvShow
import com.zaki.cinemaapp.core.data.source.local.room.CinemaDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mCinemaDao: CinemaDao) {

    fun getListMovie(): Flow<List<DataMovie>> = mCinemaDao.getMovie()

    fun getFavoriteMovie(): Flow<List<DataMovie>> = mCinemaDao.getFavoriteMovie()

    suspend fun insertMovie(movie: List<DataMovie>) = mCinemaDao.insertMovie(movie)

    fun setFavoriteMovie(movie: DataMovie, newState: Boolean) {
        movie.favorite = newState
        mCinemaDao.updateMovie(movie)
    }

    fun getListTvShow(): Flow<List<DataTvShow>> = mCinemaDao.getTvShow()

    fun getFavoriteTvShow(): Flow<List<DataTvShow>> = mCinemaDao.getFavoriteTvShow()

    suspend fun insertTvShow(tvShow: List<DataTvShow>) = mCinemaDao.insertTvShow(tvShow)

    fun setFavoriteTvShow(tvShow: DataTvShow, newState: Boolean) {
        tvShow.favorite = newState
        mCinemaDao.updateTvShow(tvShow)
    }
}