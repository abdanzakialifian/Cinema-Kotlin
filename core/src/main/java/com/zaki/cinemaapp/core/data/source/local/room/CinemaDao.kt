package com.zaki.cinemaapp.core.data.source.local.room

import androidx.room.*
import com.zaki.cinemaapp.core.data.source.local.entity.DataMovie
import com.zaki.cinemaapp.core.data.source.local.entity.DataTvShow
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaDao {
    // Dao Movie
    @Query("SELECT * FROM tbMovie")
    fun getMovie(): Flow<List<DataMovie>>

    @Query("SELECT * FROM tbMovie WHERE favorite = 1")
    fun getFavoriteMovie(): Flow<List<DataMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<DataMovie>)

    @Update
    fun updateMovie(movie: DataMovie)


    // Dao Tv Show
    @Query("SELECT * FROM tbTvShow")
    fun getTvShow(): Flow<List<DataTvShow>>

    @Query("SELECT * FROM tbTvShow WHERE favorite = 1")
    fun getFavoriteTvShow(): Flow<List<DataTvShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShow: List<DataTvShow>)

    @Update
    fun updateTvShow(tvShow: DataTvShow)
}