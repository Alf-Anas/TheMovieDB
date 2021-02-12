package com.lofrus.themoviedb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.vo.Resource

interface TheMovieDBDataSource {

    fun getListMovies(): LiveData<Resource<List<MovieEntity>>>

    fun getListTVShow(): LiveData<Resource<List<MovieEntity>>>

    fun getListMoviesBookmark(): LiveData<List<MovieEntity>>

    fun getListTVShowBookmark(): LiveData<List<MovieEntity>>

    fun getStatusError(): MutableLiveData<String?>

    fun setMoviesDetail(movie_id: Int)

    fun setTVShowDetail(movie_id: Int)

    fun getMovieDetail(): MutableLiveData<DetailMovieEntity>

}