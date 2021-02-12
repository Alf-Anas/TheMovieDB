package com.lofrus.themoviedb.model

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lofrus.themoviedb.DetailMovieActivity
import com.lofrus.themoviedb.R
import com.lofrus.themoviedb.databinding.ItemGridMovieBinding
import com.lofrus.themoviedb.databinding.ItemRowMovieBinding
import com.lofrus.themoviedb.fragment.MoviesFragment.Companion.MOVIES_
import com.lofrus.themoviedb.fragment.MoviesFragment.Companion.MOVIES_BOOKMARK_
import com.lofrus.themoviedb.fragment.MoviesFragment.Companion.TV_SHOW_
import com.lofrus.themoviedb.fragment.MoviesFragment.Companion.TV_SHOW_BOOKMARK_

class ListMovieAdapter(private val activity: Activity, typeMovie: Int?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val posterWidth = 220
    private val posterHeight = 330
    private val mData = ArrayList<MovieEntity>()
    private var _typeMovie = typeMovie

    fun setData(items: List<MovieEntity>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        if (_typeMovie == MOVIES_BOOKMARK_ || _typeMovie == TV_SHOW_BOOKMARK_) {
            return ListViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_movie, viewGroup, false))
        }
        return GridViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_grid_movie, viewGroup, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (_typeMovie == MOVIES_ || _typeMovie == TV_SHOW_) {
            (holder as GridViewHolder).bind(mData[position])
        } else if (_typeMovie == MOVIES_BOOKMARK_ || _typeMovie == TV_SHOW_BOOKMARK_) {
            (holder as ListViewHolder).bind(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowMovieBinding.bind(itemView)
        fun bind(movie: MovieEntity) {
            binding.itemRowMovieTVTitle.text = movie.title
            binding.itemRowMovieTVDate.text = movie.date
            binding.itemRowMovieTVRating.text = movie.rating.toString()
            Glide.with(itemView.context)
                .load(movie.poster)
                .apply(
                    RequestOptions()
                        .error(R.drawable.ic_baseline_broken_image_24)
                        .placeholder(R.drawable.ic_baseline_image_search_24)
                        .override(posterWidth, posterHeight)
                )
                .into(binding.itemRowMovieIMGPoster)

            itemView.setOnClickListener {
                val setObjectIntent = Intent(activity, DetailMovieActivity::class.java)
                setObjectIntent.putExtra(DetailMovieActivity.DETAIL_MOVIE, movie)
                activity.startActivity(setObjectIntent)
            }
        }
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGridMovieBinding.bind(itemView)
        fun bind(movie: MovieEntity) {
            binding.itemMovieTVTitle.text = movie.title
            binding.itemMovieTVDate.text = movie.date
            binding.itemMovieTVRating.text = movie.rating.toString()
            Glide.with(itemView.context)
                .load(movie.poster)
                .apply(
                    RequestOptions()
                        .error(R.drawable.ic_baseline_broken_image_24)
                        .placeholder(R.drawable.ic_baseline_image_search_24)
                        .override(posterWidth, posterHeight)
                )
                .into(binding.itemMovieIMGPoster)

            itemView.setOnClickListener {
                val setObjectIntent = Intent(activity, DetailMovieActivity::class.java)
                setObjectIntent.putExtra(DetailMovieActivity.DETAIL_MOVIE, movie)
                activity.startActivity(setObjectIntent)
            }
        }
    }
}

