package com.zaki.cinemaapp.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.zaki.cinemaapp.BuildConfig
import com.zaki.cinemaapp.R
import com.zaki.cinemaapp.core.domain.model.Movie
import com.zaki.cinemaapp.core.domain.model.TvShow
import com.zaki.cinemaapp.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModelMovie: DetailMovieViewModel by viewModel()
    private val viewModelTvShow: DetailTvShowViewModel by viewModel()

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val type = intent.getStringExtra(EXTRA_TYPE)
        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        val tvShow = intent.getParcelableExtra<TvShow>(EXTRA_TV_SHOW)

        if (type == "movie") {
            populateMovie(movie!!)
        } else {
            populateTvShow(tvShow!!)
        }

        binding?.imgBack?.setOnClickListener {
            onBackPressed()
        }

        binding?.imgShare?.setOnClickListener {
            shareMovie()
        }
    }

    private fun shareMovie() {
        val intent = Intent()
        val shareText = getString(R.string.share, "${binding?.tvTitle?.text}")
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, shareText)
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, getString(R.string.share_to)))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        android.R.id.home
    }

    private fun populateMovie(dataMovie: Movie) {
        binding?.apply {
            tvTitle.text = dataMovie.title
            tvRelease.text = dataMovie.release
            tvScore.text = dataMovie.score
            tvOverView.text = dataMovie.overview

            var statusFavorite = dataMovie.favorite

            setFavoriteState(statusFavorite)
            imgSetFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                setFavoriteMovie(dataMovie, statusFavorite)
                setFavoriteState(statusFavorite)
            }
        }

        Glide.with(this)
            .load(BuildConfig.IMAGE_PROFILE + dataMovie.imagePath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(binding?.imgProfile!!)

        Glide.with(this)
            .load(BuildConfig.IMAGE_BACKGROUND + dataMovie.imageBackground)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(binding?.imgBackground!!)
    }

    private fun populateTvShow(dataTvShow: TvShow) {
        binding?.apply {
            tvTitle.text = dataTvShow.title
            tvRelease.text = dataTvShow.release
            tvScore.text = dataTvShow.score
            tvOverView.text = dataTvShow.overview

            var statusFavorite = dataTvShow.favorite

            setFavoriteState(statusFavorite)
            imgSetFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                setFavoriteTvShow(dataTvShow, statusFavorite)
                setFavoriteState(statusFavorite)
            }
        }

        Glide.with(this)
            .load(BuildConfig.IMAGE_PROFILE + dataTvShow.imagePath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(binding?.imgProfile!!)

        Glide.with(this)
            .load(BuildConfig.IMAGE_BACKGROUND + dataTvShow.imageBackground)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(binding?.imgBackground!!)
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }

    private fun setFavoriteState(status: Boolean) {
        if (status) {
            binding?.imgSetFavorite?.setImageResource(R.drawable.ic_favorite)
        } else {
            binding?.imgSetFavorite?.setImageResource(R.drawable.ic_not_favorite)
        }
    }

    private fun setFavoriteMovie(movie: Movie, status: Boolean) {
        if (!status) {
            showSnackBar("${movie.title} Removed from favorite")
        } else {
            showSnackBar("${movie.title} Added to favorite")
        }
        viewModelMovie.setFavoriteMovie(movie, status)
    }

    private fun setFavoriteTvShow(tvShow: TvShow, status: Boolean) {
        if (!status) {
            showSnackBar("${tvShow.title} Removed from favorite")
        } else {
            showSnackBar("${tvShow.title} Added to Favorite")
        }
        viewModelTvShow.setFavoriteTvShow(tvShow, status)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV_SHOW = "extra_tvShow"
        const val EXTRA_TYPE = "extra_type"
    }
}