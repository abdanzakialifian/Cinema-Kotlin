package com.zaki.cinemaapp.core.utils

import com.zaki.cinemaapp.core.data.source.local.entity.DataMovie
import com.zaki.cinemaapp.core.data.source.local.entity.DataTvShow
import com.zaki.cinemaapp.core.data.source.remote.response.ResultsItem
import com.zaki.cinemaapp.core.data.source.remote.response.ResultsShow
import com.zaki.cinemaapp.core.domain.model.Movie
import com.zaki.cinemaapp.core.domain.model.TvShow

object DataMapper {

    // Data Mapper Movie
    fun movieResponseEntities(input: List<ResultsItem>): List<DataMovie> {
        val movieList = ArrayList<DataMovie>()
        input.map {
            val movie = DataMovie(
                id = it.id,
                title = it.title,
                imagePath = it.posterPath,
                release = it.releaseDate,
                score = it.voteAverage.toString(),
                overview = it.overview,
                imageBackground = it.backdropPath,
                favorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun movieEntitiesToDomain(input: List<DataMovie>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                imagePath = it.imagePath,
                release = it.release,
                score = it.score,
                overview = it.overview,
                imageBackground = it.imageBackground,
                favorite = it.favorite
            )
        }

    fun movieDomainToEntity(input: Movie) =
        DataMovie(
            id = input.id,
            title = input.title,
            imagePath = input.imagePath,
            release = input.release,
            score = input.score,
            overview = input.overview,
            imageBackground = input.imageBackground,
            favorite = input.favorite
        )


    // Data Mapper TvShow
    fun tvShowResponseEntities(input: List<ResultsShow>): List<DataTvShow> {
        val tvShowList = ArrayList<DataTvShow>()
        input.map {
            val movie = DataTvShow(
                id = it.id,
                title = it.name,
                imagePath = it.posterPath,
                release = it.firstAirDate,
                score = it.voteAverage.toString(),
                overview = it.overview,
                imageBackground = it.backdropPath,
                favorite = false
            )
            tvShowList.add(movie)
        }
        return tvShowList
    }

    fun tvShowEntitiesToDomain(input: List<DataTvShow>): List<TvShow> =
        input.map {
            TvShow(
                id = it.id,
                title = it.title,
                imagePath = it.imagePath,
                release = it.release,
                score = it.score,
                overview = it.overview,
                imageBackground = it.imageBackground,
                favorite = it.favorite
            )
        }

    fun tvShowDomainToEntity(input: TvShow) =
        DataTvShow(
            id = input.id,
            title = input.title,
            imagePath = input.imagePath,
            release = input.release,
            score = input.score,
            overview = input.overview,
            imageBackground = input.imageBackground,
            favorite = input.favorite
        )
}