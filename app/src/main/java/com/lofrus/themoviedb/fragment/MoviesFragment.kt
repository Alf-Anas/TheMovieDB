package com.lofrus.themoviedb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.lofrus.themoviedb.databinding.MoviesFragmentBinding
import com.lofrus.themoviedb.model.ListMovieAdapter

class MoviesFragment : Fragment() {

    companion object {
        private const val TYPE = "type"

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = ListMovieAdapter()
        adapter.notifyDataSetChanged()

        binding.moviesRV.layoutManager = GridLayoutManager(activity, gridColumn)
        binding.moviesRV.adapter = adapter

        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        val typeMovie = arguments?.getInt(TYPE, 0)

        if (typeMovie == 0) {
            viewModel.setListMovies()
        } else if (typeMovie == 1) {
            viewModel.setListTVShow()
        }

        viewModel.getListMovie().observe(this, { listMovie ->
            if (listMovie != null) {
                adapter.setData(listMovie)
            }
            showProgressBar(false)
        })

        viewModel.statusError.observe(this, { status ->
            if (status != null) {
                Toast.makeText(activity, status, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun showProgressBar(state: Boolean) {
        binding.moviesProgressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

}