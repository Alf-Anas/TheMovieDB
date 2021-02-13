package com.lofrus.themoviedb

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lofrus.themoviedb.databinding.ActivityDetailMovieBinding
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.room.MovieBookmarkDatabase
import com.lofrus.themoviedb.room.MovieBookmarkEntity
import com.lofrus.themoviedb.utils.EspressoIdlingResource
import com.lofrus.themoviedb.viewmodel.DetailViewModel
import com.lofrus.themoviedb.viewmodel.ViewModelFactory
import kotlin.concurrent.thread

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val DETAIL_MOVIE = "detail_movie"
    }

    private val posterWidth = 220
    private val posterHeight = 330
    private val backdropWidth = 1920
    private val backdropHeight = 800

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var localDb: MovieBookmarkDatabase
    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        localDb = MovieBookmarkDatabase.getAppDatabase(this)!!

        val movieEntity = intent.getParcelableExtra<MovieEntity>(DETAIL_MOVIE) as MovieEntity
        val typeMovie = movieEntity.type
        if (typeMovie == 0) {
            viewModel.setMoviesDetail(movieEntity.id)
        } else if (typeMovie == 1) {
            viewModel.setTVShowDetail(movieEntity.id)
        }

        binding.detailFABBookmark.setOnClickListener {
            thread {
                if (!isBookmarked) {
                    val movieBookmarkEntity = MovieBookmarkEntity(
                        movieEntity.id,
                        movieEntity.type,
                        movieEntity.title,
                        movieEntity.date,
                        movieEntity.rating,
                        movieEntity.poster,
                        true
                    )
                    localDb.movieBookmarkDao().update(movieBookmarkEntity)
                } else {
                    localDb.movieBookmarkDao().update(
                        MovieBookmarkEntity(
                            movieEntity.id,
                            movieEntity.type,
                            movieEntity.title,
                            movieEntity.date,
                            movieEntity.rating,
                            movieEntity.poster,
                            false
                        )
                    )
                }
                if (localDb.movieBookmarkDao().getBookmarkWithId(movieEntity.id).count() > 0) {
                    bookmarkStatus(true)
                    toastShort(getString(R.string.toast_bookmarked))
                } else {
                    bookmarkStatus(false)
                    toastShort(getString(R.string.toast_remove_bookmark))
                }
            }
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
                            .override(posterWidth, posterHeight)
                    )
                    .into(binding.detailIMGPoster)
                Glide.with(this)
                    .load(detailMovie.backdrop)
                    .apply(
                        RequestOptions()
                            .error(R.drawable.ic_baseline_broken_image_24)
                            .placeholder(R.drawable.ic_baseline_image_search_24)
                            .override(backdropWidth, backdropHeight)
                    )
                    .into(binding.detailIMGBackdrop)

                thread {
                    val dataBookmark = localDb.movieBookmarkDao().getBookmarkWithId(movieEntity.id)
                    if (dataBookmark.count() > 0) {
                        bookmarkStatus(true)
                    }
                }
            }
            showProgressBar(false)
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        })

        viewModel.getStatusError().observe(this, { status ->
            if (status != null) {
                toastShort(status)
            }
        })

    }

    private fun showProgressBar(state: Boolean) {
        binding.detailProgressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun bookmarkStatus(bookmark: Boolean) {
        isBookmarked = bookmark
        if (bookmark) {
            binding.detailFABBookmark.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_bookmark_24)
            )
        } else {
            binding.detailFABBookmark.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_bookmark_border_24)
            )
        }
    }

    private fun toastShort(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}