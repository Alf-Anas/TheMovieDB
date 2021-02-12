package com.lofrus.themoviedb.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lofrus.themoviedb.data.TheMovieDBRepository
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.vo.Resource

class MoviesViewModel(private val theMovieDBRepository: TheMovieDBRepository) : ViewModel() {

    fun getListMovies(): LiveData<Resource<List<MovieEntity>>> {
        return theMovieDBRepository.getListMovies()
    }

    fun getListTVShow(): LiveData<Resource<List<MovieEntity>>> {
        return theMovieDBRepository.getListTVShow()
    }

    fun getListMoviesBookmark(): LiveData<List<MovieEntity>> {
        return theMovieDBRepository.getListMoviesBookmark()
    }

    fun getListTVShowBookmark(): LiveData<List<MovieEntity>> {
        return theMovieDBRepository.getListTVShowBookmark()
    }


    // For Dummy Unit Testing
    fun getListMoviesDummy(): List<MovieEntity> {
        return theMovieDBRepository.getListMoviesDummy()
    }

    fun getListTVShowDummy(): List<MovieEntity> {
        return theMovieDBRepository.getListTVShowDummy()
    }

}