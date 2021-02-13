package com.lofrus.themoviedb.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.lofrus.themoviedb.data.TheMovieDBRepository
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.vo.Resource

class MoviesViewModel(private val theMovieDBRepository: TheMovieDBRepository) : ViewModel() {

    fun getListMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return theMovieDBRepository.getListMovies()
    }

    fun getListTVShow(): LiveData<Resource<PagedList<MovieEntity>>> {
        return theMovieDBRepository.getListTVShow()
    }

    fun getListMoviesBookmark(): LiveData<PagedList<MovieEntity>> {
        return theMovieDBRepository.getListMoviesBookmark()
    }

    fun getListTVShowBookmark(): LiveData<PagedList<MovieEntity>> {
        return theMovieDBRepository.getListTVShowBookmark()
    }

}