package com.zaki.cinemaapp.core.di

import androidx.room.Room
import com.zaki.cinemaapp.core.data.api.ApiService
import com.zaki.cinemaapp.core.data.source.CinemaRepository
import com.zaki.cinemaapp.core.data.source.local.LocalDataSource
import com.zaki.cinemaapp.core.data.source.local.room.CinemaDatabase
import com.zaki.cinemaapp.core.data.source.remote.RemoteDataSource
import com.zaki.cinemaapp.core.domain.repository.ICinemaRepository
import com.zaki.cinemaapp.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<CinemaDatabase>().cinemaDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            CinemaDatabase::class.java, "CinemaDb"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<ICinemaRepository> {
        CinemaRepository(
            get(),
            get(),
            get()
        )
    }
}