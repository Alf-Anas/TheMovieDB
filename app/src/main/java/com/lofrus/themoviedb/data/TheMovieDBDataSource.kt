package com.lofrus.themoviedb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.vo.Resource

interface TheMovieDBDataSource {

    fun getListMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getListTVShow(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getListMoviesBookmark(): LiveData<PagedList<MovieEntity>>

    fun getListTVShowBookmark(): LiveData<PagedList<MovieEntity>>

    fun getStatusError(): MutableLiveData<String?>

    fun setMoviesDetail(movie_id: Int)

    fun setTVShowDetail(movie_id: Int)

    fun getMovieDetail(): MutableLiveData<DetailMovieEntity>

}