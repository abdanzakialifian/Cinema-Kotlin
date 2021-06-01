package com.zaki.cinemaapp.core.data.source

import com.zaki.cinemaapp.core.data.source.local.LocalDataSource
import com.zaki.cinemaapp.core.data.source.remote.network.ApiResponse
import com.zaki.cinemaapp.core.data.source.remote.RemoteDataSource
import com.zaki.cinemaapp.core.data.source.remote.response.ResultsItem
import com.zaki.cinemaapp.core.data.source.remote.response.ResultsShow
import com.zaki.cinemaapp.core.domain.model.Movie
import com.zaki.cinemaapp.core.domain.model.TvShow
import com.zaki.cinemaapp.core.domain.repository.ICinemaRepository
import com.zaki.cinemaapp.core.utils.AppExecutors
import com.zaki.cinemaapp.core.utils.DataMapper
import com.zaki.cinemaapp.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CinemaRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ICinemaRepository {

    override fun getListMovie(): Flow<Resource<List<Movie>>> {
        return object :
            NetworkBoundResource<List<Movie>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getListMovie().map { DataMapper.movieEntitiesToDomain(it) }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getMovie()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val movieList = DataMapper.movieResponseEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovie().map { DataMapper.movieEntitiesToDomain(it) }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.movieDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

    override fun getListTvShow(): Flow<Resource<List<TvShow>>> {
        return object :
            NetworkBoundResource<List<TvShow>, List<ResultsShow>>() {
            override fun loadFromDB(): Flow<List<TvShow>> =
                localDataSource.getListTvShow().map { DataMapper.tvShowEntitiesToDomain(it) }

            override fun shouldFetch(data: List<TvShow>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsShow>>> =
                remoteDataSource.getTvShow()

            override suspend fun saveCallResult(data: List<ResultsShow>) {
                val tvShowList = DataMapper.tvShowResponseEntities(data)
                localDataSource.insertTvShow(tvShowList)
            }
        }.asFlow()
    }

    override fun getFavoriteTvShow(): Flow<List<TvShow>> =
        localDataSource.getFavoriteTvShow().map { DataMapper.tvShowEntitiesToDomain(it) }

    override fun setFavoriteTvShow(tvShow: TvShow, state: Boolean) {
        val tvShowEntity = DataMapper.tvShowDomainToEntity(tvShow)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(tvShowEntity, state) }
    }
}