package com.zaki.cinemaapp.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaki.cinemaapp.R
import com.zaki.cinemaapp.core.ui.MovieAdapter
import com.zaki.cinemaapp.core.vo.Resource
import com.zaki.cinemaapp.databinding.FragmentMovieBinding
import com.zaki.cinemaapp.detail.DetailActivity
import com.zaki.cinemaapp.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.zaki.cinemaapp.detail.DetailActivity.Companion.EXTRA_TYPE
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            activity,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            activity,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            activity,
            R.anim.from_button_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            activity,
            R.anim.to_button_anim
        )
    }

    private var clicked = false
    private val viewModel: MovieViewModel by viewModel()

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(EXTRA_MOVIE, selectedData)
                intent.putExtra(EXTRA_TYPE, "movie")
                startActivity(intent)
            }

            with(binding?.rvMovie!!) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }

            viewModel.getMovie().observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            movieAdapter.setMovie(movie.data)
                        }
                        is Resource.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        binding?.fabAddFavoriteMovie?.setOnClickListener {
            onAddButtonClick()
        }

        binding?.fabFavoriteMovie1?.setOnClickListener {
            val uri = Uri.parse("cinemaapp://favorite")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.putExtra("type", "movie")
            startActivity(intent)
        }

        binding?.fabFavoriteTvShow1?.setOnClickListener {
            val uri = Uri.parse("cinemaapp://favorite")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.putExtra("type", "tvShow")
            startActivity(intent)
        }
    }

    private fun onAddButtonClick() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding?.fabFavoriteMovie1?.visibility = View.VISIBLE
            binding?.fabFavoriteTvShow1?.visibility = View.VISIBLE
            binding?.fabAddFavoriteMovie?.setImageResource(R.drawable.ic_favorite)
        } else {
            binding?.fabFavoriteMovie1?.visibility = View.INVISIBLE
            binding?.fabFavoriteTvShow1?.visibility = View.INVISIBLE
            binding?.fabAddFavoriteMovie?.setImageResource(R.drawable.ic_not_favorite)
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding?.fabFavoriteMovie1?.startAnimation(fromBottom)
            binding?.fabFavoriteTvShow1?.startAnimation(fromBottom)
            binding?.fabAddFavoriteMovie?.startAnimation(rotateOpen)
        } else {
            binding?.fabFavoriteMovie1?.startAnimation(toBottom)
            binding?.fabFavoriteTvShow1?.startAnimation(toBottom)
            binding?.fabAddFavoriteMovie?.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding?.fabFavoriteMovie1?.isClickable = true
            binding?.fabFavoriteTvShow1?.isClickable = true
        } else {
            binding?.fabFavoriteMovie1?.isClickable = false
            binding?.fabFavoriteTvShow1?.isClickable = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}