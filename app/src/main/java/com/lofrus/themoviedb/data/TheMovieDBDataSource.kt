package com.lofrus.themoviedb.data

import androidx.lifecycle.MutableLiveData
import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.model.MovieEntity

interface TheMovieDBDataSource {

    fun setListMovies()

    fun setListTVShow()

    fun getListMovie(): MutableLiveData<ArrayList<MovieEntity>>

    fun getStatusError(): MutableLiveData<String?>

    fun setMoviesDetail(movie_id: Int)

    fun setTVShowDetail(movie_id: Int)

    fun getMovieDetail(): MutableLiveData<DetailMovieEntity>

}