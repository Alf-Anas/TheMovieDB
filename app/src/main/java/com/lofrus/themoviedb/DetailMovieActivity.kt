package com.lofrus.themoviedb

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lofrus.themoviedb.databinding.ActivityDetailMovieBinding
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.utils.EspressoIdlingResource
import com.lofrus.themoviedb.viewmodel.DetailViewModel
import com.lofrus.themoviedb.viewmodel.ViewModelFactory

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val DETAIL_MOVIE = "detail_movie"
    }

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val movieEntity = intent.getParcelableExtra<MovieEntity>(DETAIL_MOVIE) as MovieEntity
        val typeMovie = movieEntity.type
        if (typeMovie == 0) {
            viewModel.setMoviesDetail(movieEntity.id)
        } else if (typeMovie == 1) {
            viewModel.setTVShowDetail(movieEntity.id)
        }

        EspressoIdlingResource.increment()
        viewModel.getMovieDetail().observe(this, { detailMovie ->
            if (detailMovie != null) {
                binding.detailTVTitle.text = detailMovie.title
                binding.detailTVDate.text = detailMovie.date
                binding.detailTVGenre.text = detailMovie.genre
                binding.detailTVOverview.text = detailMovie.overview
                binding.detailTVLink.text = detailMovie.link
                binding.detailTVRating.text = detailMovie.rating.toString()
                Glide.with(this)
                    .load(detailMovie.poster)
                    .apply(
                        RequestOptions()
                            .error(R.drawable.ic_baseline_broken_image_24)
                            .placeholder(R.drawable.ic_baseline_image_search_24)
                            .override(220, 330)
                    )
                    .into(binding.detailIMGPoster)
                Glide.with(this)
                    .load(detailMovie.backdrop)
                    .apply(
                        RequestOptions()
                            .error(R.drawable.ic_baseline_broken_image_24)
                            .placeholder(R.drawable.ic_baseline_image_search_24)
                            .override(1920, 800)
                    )
                    .into(binding.detailIMGBackdrop)
            }
            showProgressBar(false)
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        })

        viewModel.getStatusError().observe(this, { status ->
            if (status != null) {
                Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
                if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
            }
        })

    }

    private fun showProgressBar(state: Boolean) {
        binding.detailProgressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}