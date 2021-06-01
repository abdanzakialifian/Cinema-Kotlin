package com.zaki.cinemaapp.tvshow

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
import com.zaki.cinemaapp.core.ui.TvShowAdapter
import com.zaki.cinemaapp.core.vo.Resource
import com.zaki.cinemaapp.databinding.FragmentTvShowBinding
import com.zaki.cinemaapp.detail.DetailActivity
import com.zaki.cinemaapp.detail.DetailActivity.Companion.EXTRA_TV_SHOW
import com.zaki.cinemaapp.detail.DetailActivity.Companion.EXTRA_TYPE
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

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
    private val viewModel: TvShowViewModel by viewModel()

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {

            val tvShowAdapter = TvShowAdapter()
            tvShowAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(EXTRA_TV_SHOW, selectedData)
                intent.putExtra(EXTRA_TYPE, "tvShow")
                startActivity(intent)
            }

            viewModel.getTvShow().observe(viewLifecycleOwner, { tvShow ->
                if (tvShow != null) {
                    when (tvShow) {
                        is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            tvShowAdapter.setTvShow(tvShow.data)
                        }
                        is Resource.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
            with(binding?.rvTvShow!!) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
        }

        binding?.fabAddFavoriteTvShow?.setOnClickListener {
            onAddButtonClick()
        }

        binding?.fabFavoriteMovie2?.setOnClickListener {
            val uri = Uri.parse("cinemaapp://favorite")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.putExtra("type", "movie")
            startActivity(intent)
        }

        binding?.fabFavoriteTvShow2?.setOnClickListener {
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
            binding?.fabFavoriteMovie2?.visibility = View.VISIBLE
            binding?.fabFavoriteTvShow2?.visibility = View.VISIBLE
            binding?.fabAddFavoriteTvShow?.setImageResource(R.drawable.ic_favorite)
        } else {
            binding?.fabFavoriteMovie2?.visibility = View.INVISIBLE
            binding?.fabFavoriteTvShow2?.visibility = View.INVISIBLE
            binding?.fabAddFavoriteTvShow?.setImageResource(R.drawable.ic_not_favorite)
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding?.fabFavoriteMovie2?.startAnimation(fromBottom)
            binding?.fabFavoriteTvShow2?.startAnimation(fromBottom)
            binding?.fabAddFavoriteTvShow?.startAnimation(rotateOpen)
        } else {
            binding?.fabFavoriteMovie2?.startAnimation(toBottom)
            binding?.fabFavoriteTvShow2?.startAnimation(toBottom)
            binding?.fabAddFavoriteTvShow?.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding?.fabFavoriteMovie2?.isClickable = true
            binding?.fabFavoriteTvShow2?.isClickable = true
        } else {
            binding?.fabFavoriteMovie2?.isClickable = false
            binding?.fabFavoriteTvShow2?.isClickable = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}