package com.zaki.cinemaapp.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zaki.cinemaapp.R

class ContainerFavorite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container_favorite)

        val mFragmentManager = supportFragmentManager

        val mTvShowFavoriteFragment = TvShowFavoriteFragment()

        val mMovieFavoriteFragment = MovieFavoriteFragment()

        val type = intent.getStringExtra("type")
        if (type == "movie") {
            mFragmentManager
                .beginTransaction()
                .add(
                    R.id.frame_container_favorite,
                    mMovieFavoriteFragment,
                    MovieFavoriteFragment::class.java.simpleName
                )
                .commit()
        } else {
            mFragmentManager
                .beginTransaction()
                .add(
                    R.id.frame_container_favorite,
                    mTvShowFavoriteFragment,
                    TvShowFavoriteFragment::class.java.simpleName
                )
                .commit()
        }
    }
}