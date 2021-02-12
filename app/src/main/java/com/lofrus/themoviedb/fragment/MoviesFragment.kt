package com.lofrus.themoviedb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lofrus.themoviedb.databinding.MoviesFragmentBinding
import com.lofrus.themoviedb.model.ListMovieAdapter
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.utils.EspressoIdlingResource
import com.lofrus.themoviedb.viewmodel.ViewModelFactory
import com.lofrus.themoviedb.vo.Resource
import com.lofrus.themoviedb.vo.Status

class MoviesFragment : Fragment() {

    companion object {
        const val MOVIES_ = 0
        const val TV_SHOW_ = 1
        const val MOVIES_BOOKMARK_ = 10
        const val TV_SHOW_BOOKMARK_ = 11
        const val TYPE = "type"

        fun newInstance(type: Int) = MoviesFragment().apply {
            arguments = Bundle().apply {
                putInt(TYPE, type)
            }
        }
    }

    private val gridColumn = 2
    private lateinit var binding: MoviesFragmentBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: ListMovieAdapter
    private var typeMovie: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]

        typeMovie = arguments?.getInt(TYPE, MOVIES_) ?: 0
        adapter = ListMovieAdapter(requireActivity(), typeMovie)
        adapter.notifyDataSetChanged()

        if (typeMovie == MOVIES_ || typeMovie == TV_SHOW_) {
            binding.moviesRV.layoutManager = GridLayoutManager(activity, gridColumn)
        } else if (typeMovie == MOVIES_BOOKMARK_ || typeMovie == TV_SHOW_BOOKMARK_) {
            binding.moviesRV.layoutManager = LinearLayoutManager(activity)
        }
        binding.moviesRV.adapter = adapter

        EspressoIdlingResource.increment()
        when (typeMovie) {
            MOVIES_ -> {
                viewModel.getListMovies().observe(this, { listMovie ->
                    setMovieData(listMovie)
                })
            }
            TV_SHOW_ -> {
                viewModel.getListTVShow().observe(this, { listMovie ->
                    setMovieData(listMovie)
                })
            }
            MOVIES_BOOKMARK_ -> {
                viewModel.getListMoviesBookmark().observe(this, { listMovie ->
                    setBookmarkData(listMovie)
                })
            }
            TV_SHOW_BOOKMARK_ -> {
                viewModel.getListTVShowBookmark().observe(this, { listMovie ->
                    setBookmarkData(listMovie)
                })
            }
        }

    }

    private fun setBookmarkData(listMovie: List<MovieEntity>?) {
        if (listMovie != null) {
            adapter.setData(listMovie)
        }
        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        showProgressBar(false)
    }

    private fun setMovieData(listMovie: Resource<List<MovieEntity>>?) {
        if (listMovie != null) {
            when (listMovie.status) {
                Status.LOADING -> showProgressBar(true)
                Status.SUCCESS -> {
                    showProgressBar(false)
                    listMovie.data?.let { adapter.setData(it) }
                }
                Status.ERROR -> {
                    showProgressBar(false)
                    Toast.makeText(context, listMovie.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
            EspressoIdlingResource.decrement()
        }
    }

    private fun showProgressBar(state: Boolean) {
        binding.moviesProgressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        when (typeMovie) {
            MOVIES_BOOKMARK_ -> {
                viewModel.getListMoviesBookmark().observe(this, { listMovie ->
                    setBookmarkData(listMovie)
                })
            }
            TV_SHOW_BOOKMARK_ -> {
                viewModel.getListTVShowBookmark().observe(this, { listMovie ->
                    setBookmarkData(listMovie)
                })
            }
        }
    }

}