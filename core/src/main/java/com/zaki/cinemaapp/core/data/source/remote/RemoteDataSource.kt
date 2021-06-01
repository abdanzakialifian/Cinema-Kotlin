package com.zaki.cinemaapp.core.data.source.remote

import android.util.Log
import com.zaki.cinemaapp.core.data.api.ApiService
import com.zaki.cinemaapp.core.data.source.remote.network.ApiResponse
import com.zaki.cinemaapp.core.data.source.remote.response.ResultsItem
import com.zaki.cinemaapp.core.data.source.remote.response.ResultsShow
import com.zaki.cinemaapp.core.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(private val apiService: ApiService) {

    suspend fun getMovie(): Flow<ApiResponse<List<ResultsItem>>> {
        return flow {
            try {
                EspressoIdlingResource.increment()
                val response = apiService.getMovie()
                val data = response.results
                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
            EspressoIdlingResource.decrement()
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(movieId: Int): Flow<ApiResponse<ResultsItem>> {
        return flow {
            try {
                EspressoIdlingResource.increment()
                val response = apiService.getDetailMovie(movieId)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
            EspressoIdlingResource.decrement()
        }.flowOn(Dispatchers.IO)
    }

    fun getTvShow(): Flow<ApiResponse<List<ResultsShow>>> {
        return flow {
            try {
                EspressoIdlingResource.increment()
                val response = apiService.getTvShow()
                val data = response.results
                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
            EspressoIdlingResource.decrement()
        }.flowOn(Dispatchers.IO)
    }

    fun getTvShowDetail(tvShowId: Int): Flow<ApiResponse<ResultsShow>> {
        return flow {
            try {
                EspressoIdlingResource.increment()
                val response = apiService.getDetailTvShow(tvShowId)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
            EspressoIdlingResource.decrement()
        }.flowOn(Dispatchers.IO)
    }
}