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
import com.zaki.cinemaapp.databinding.FragmentTvShowFavoriteBinding
import com.zaki.cinemaapp.core.ui.TvShowAdapter
import com.zaki.cinemaapp.detail.DetailActivity
import com.zaki.cinemaapp.detail.DetailActivity.Companion.EXTRA_TV_SHOW
import com.zaki.cinemaapp.detail.DetailActivity.Companion.EXTRA_TYPE
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class TvShowFavoriteFragment : Fragment() {

    private lateinit var tvShowAdapter: TvShowAdapter
    private val viewModel: FavoriteViewModel by viewModel()

    private var _binding: FragmentTvShowFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvShowFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        if (activity != null) {
            itemTouchHelper.attachToRecyclerView(binding?.rvFavoriteTvShow)

            tvShowAdapter = TvShowAdapter()
            tvShowAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(EXTRA_TV_SHOW, selectedData)
                intent.putExtra(EXTRA_TYPE, "tvShow")
                startActivity(intent)
            }

            viewModel.getFavoriteTvShow().observe(viewLifecycleOwner, { favoriteTvShow ->
                if (favoriteTvShow != null) {
                    if (favoriteTvShow.isNotEmpty()) {
                        binding?.animEmpty?.visibility = View.GONE
                    } else {
                        binding?.animEmpty?.visibility = View.VISIBLE
                    }
                    tvShowAdapter.setTvShow(favoriteTvShow)
                }

                with(binding?.rvFavoriteTvShow!!) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = tvShowAdapter
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
                val courseEntity = tvShowAdapter.getSwipedData(swipedPosition)
                courseEntity?.let { dataTvShow ->
                    viewModel.setTvShow(dataTvShow)
                }
                val snackBar =
                    Snackbar.make(view as View, R.string.message_cancel, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_ok) { v ->
                    courseEntity?.let { dataTvShow ->
                        viewModel.setTvShow(dataTvShow)
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