package com.lofrus.themoviedb.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lofrus.themoviedb.data.TheMovieDBRepository
import com.lofrus.themoviedb.model.MovieEntity

class MoviesViewModel(private val theMovieDBRepository: TheMovieDBRepository) : ViewModel() {

    fun setListMovies(){
        theMovieDBRepository.setListMovies()
    }

    fun setListTVShow(){
        theMovieDBRepository.setListTVShow()
    }

    fun getListMovie(): LiveData<ArrayList<MovieEntity>> {
        return theMovieDBRepository.getListMovie()
    }

    fun getStatusError() : LiveData<String?>{
        return theMovieDBRepository.getStatusError()
    }

    // For Dummy Unit Testing
    fun getListMoviesDummy(): List<MovieEntity> {
        return theMovieDBRepository.getListMoviesDummy()
    }

    fun getListTVShowDummy(): List<MovieEntity> {
        return theMovieDBRepository.getListTVShowDummy()
    }

}