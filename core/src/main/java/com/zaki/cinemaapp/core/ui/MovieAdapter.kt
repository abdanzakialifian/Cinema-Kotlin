package com.zaki.cinemaapp.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zaki.cinemaapp.core.BuildConfig
import com.zaki.cinemaapp.core.R
import com.zaki.cinemaapp.core.domain.model.Movie
import com.zaki.cinemaapp.core.databinding.ItemsMovieBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var listMovie = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setMovie(movies: List<Movie>?) {
        if (movies == null) return
        this.listMovie.clear()
        this.listMovie.addAll(movies)
        this.notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_PROFILE + movies.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgMovie)
                tvTitle.text = movies.title
                tvScore.text = movies.score
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listMovie[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding =
            ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    fun getSwipedData(swipedPosition: Int): Movie = listMovie[swipedPosition]

    override fun getItemCount(): Int = listMovie.size
}