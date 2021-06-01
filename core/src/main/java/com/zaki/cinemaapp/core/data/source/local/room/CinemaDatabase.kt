package com.zaki.cinemaapp.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zaki.cinemaapp.core.data.source.local.entity.DataMovie
import com.zaki.cinemaapp.core.data.source.local.entity.DataTvShow

@Database(entities = [DataMovie::class, DataTvShow::class], version = 1, exportSchema = false)
abstract class CinemaDatabase : RoomDatabase() {
    abstract fun cinemaDao(): CinemaDao
}