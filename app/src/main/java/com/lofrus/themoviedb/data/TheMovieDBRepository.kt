package com.lofrus.themoviedb.data

import androidx.lifecycle.MutableLiveData
import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.model.MovieEntity

class TheMovieDBRepository private constructor(private val remoteDataSource: RemoteDataSource) : TheMovieDBDataSource {

    companion object {
        @Volatile
        private var instance: TheMovieDBRepository? = null

        fun getInstance(remoteData: RemoteDataSource): TheMovieDBRepository =
                instance ?: synchronized(this) {
                    instance ?: TheMovieDBRepository(remoteData)
                }
    }

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


    //For Dummy Unit Testing
    fun getListMoviesDummy(): List<MovieEntity> {
        return remoteDataSource.getListMoviesDummy()
    }

    fun getListTVShowDummy(): List<MovieEntity> {
        return remoteDataSource.getListTVShowDummy()
    }

    fun setMoviesDetailDummy(detailMovie: DetailMovieEntity) {
        remoteDataSource.setMoviesDetailDummy(detailMovie)
    }

    fun setTVShowDetailDummy(detailMovie: DetailMovieEntity) {
        remoteDataSource.setTVShowDetailDummy(detailMovie)
    }

    fun getMovieDetailDummy(movieID: Int): DetailMovieEntity {
        return remoteDataSource.getMovieDetailDummy(movieID)
    }


}