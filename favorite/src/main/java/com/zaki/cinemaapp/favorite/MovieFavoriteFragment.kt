package com.zaki.cinemaapp.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zaki.cinemaapp.R
import com.zaki.cinemaapp.databinding.FragmentMovieFavoriteBinding
import com.zaki.cinemaapp.core.ui.MovieAdapter
import com.zaki.cinemaapp.detail.DetailActivity
import com.zaki.cinemaapp.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.zaki.cinemaapp.detail.DetailActivity.Companion.EXTRA_TYPE
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class MovieFavoriteFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: FavoriteViewModel by viewModel()

    private var _binding: FragmentMovieFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.rvFavoriteMovie)

        loadKoinModules(favoriteModule)

        if (activity != null) {
            movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(EXTRA_MOVIE, selectedData)
                intent.putExtra(EXTRA_TYPE, "movie")
                startActivity(intent)
            }

            viewModel.getFavoriteMovie().observe(viewLifecycleOwner, { favoriteMovie ->
                if (favoriteMovie != null) {
                    if (favoriteMovie.isNotEmpty()) {
                        binding?.animEmpty?.visibility = View.GONE
                    } else {
                        binding?.animEmpty?.visibility = View.VISIBLE
                    }

                    movieAdapter.setMovie(favoriteMovie)
                }

                with(binding?.rvFavoriteMovie!!) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = movieAdapter
                }
            })
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.bindingAdapterPosition
                val courseEntity = movieAdapter.getSwipedData(swipedPosition)
                courseEntity.let { dataMovie ->
                    viewModel.setMovie(dataMovie)
                }
                val snackBar =
                    Snackbar.make(view as View, R.string.message_cancel, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_ok) { v ->
                    courseEntity.let { dataMovie ->
                        viewModel.setMovie(dataMovie)
                    }
                }
                snackBar.show()
            }
        }
    })

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}