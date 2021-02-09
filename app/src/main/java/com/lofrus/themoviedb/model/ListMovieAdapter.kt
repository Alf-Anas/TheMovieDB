package com.lofrus.themoviedb.model

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

class ListMovieAdapter : RecyclerView.Adapter<ListMovieAdapter.ListViewHolder>() {

    private val mData = ArrayList<MovieEntity>()
    fun setData(items: ArrayList<MovieEntity>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_grid_movie, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                        .override(220, 330)
                )
                .into(binding.itemMovieIMGPoster)

            itemView.setOnClickListener {
                val setObjectIntent = Intent(itemView.context, DetailMovieActivity::class.java)
                setObjectIntent.putExtra(DetailMovieActivity.DETAIL_MOVIE, movie)
                itemView.context.startActivity(setObjectIntent)
            }
        }
    }
}

