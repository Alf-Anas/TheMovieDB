package com.lofrus.themoviedb.data

import androidx.lifecycle.MutableLiveData
import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.model.MovieEntity

class FakeTheMovieDBRepository(private val remoteDataSource: RemoteDataSource) : TheMovieDBDataSource {

    override fun setListMovies() {
        remoteDataSource.setListMovies()
    }

    override fun setListTVShow() {
        remoteDataSource.setListTVShow()
    }

    override fun getListMovie(): MutableLiveData<ArrayList<MovieEntity>> {
        return remoteDataSource.listMovie
    }

    override fun getStatusError(): MutableLiveData<String?> {
        return remoteDataSource.statusError
    }

    override fun setMoviesDetail(movie_id: Int) {
        remoteDataSource.setMoviesDetail(movie_id)
    }

    override fun setTVShowDetail(movie_id: Int) {
        remoteDataSource.setTVShowDetail(movie_id)
    }

    override fun getMovieDetail(): MutableLiveData<DetailMovieEntity> {
       return remoteDataSource.detailMovie
    }

}