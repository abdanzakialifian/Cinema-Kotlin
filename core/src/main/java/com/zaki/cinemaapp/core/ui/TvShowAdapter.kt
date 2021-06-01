package com.zaki.cinemaapp.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zaki.cinemaapp.core.BuildConfig
import com.zaki.cinemaapp.core.R
import com.zaki.cinemaapp.core.databinding.ItemsTvShowBinding
import com.zaki.cinemaapp.core.domain.model.TvShow

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    private var listTvShow = ArrayList<TvShow>()
    var onItemClick: ((TvShow) -> Unit)? = null

    fun setTvShow(tvShows: List<TvShow>?) {
        if (tvShows == null) return
        this.listTvShow.clear()
        this.listTvShow.addAll(tvShows)
        this.notifyDataSetChanged()
    }

    inner class TvShowViewHolder(private val binding: ItemsTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_PROFILE + tvShow.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_loading)
                    .into(imgTvShow)
                tvTitle.text = tvShow.title
                tvScore.text = tvShow.score
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listTvShow[bindingAdapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsTvShowBinding =
            ItemsTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsTvShowBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(listTvShow[position])
    }

    fun getSwipedData(swipedPosition: Int): TvShow? = listTvShow[swipedPosition]

    override fun getItemCount(): Int = listTvShow.size
}