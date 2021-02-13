package com.lofrus.themoviedb.data

import androidx.paging.DataSource
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.room.MovieBookmarkDao
import com.lofrus.themoviedb.room.MovieBookmarkEntity


class LocalDataSource private constructor(private val movieBookmarkDao: MovieBookmarkDao) {

    companion object {
        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieBookmarkDao: MovieBookmarkDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieBookmarkDao)
    }

    fun getListMovies(): DataSource.Factory<Int, MovieEntity> {
        return movieBookmarkDao.getAllLocalMovies()
    }

    fun getListTVShow(): DataSource.Factory<Int, MovieEntity> {
        return movieBookmarkDao.getAllLocalTVShow()
    }

    fun insertMovieToLocal(tempListMovie: List<MovieBookmarkEntity>) = movieBookmarkDao.insertMovieToLocal(tempListMovie)

    fun getListMoviesBookmark(): DataSource.Factory<Int, MovieEntity> {
        return movieBookmarkDao.getAllBookmarkMovies()
    }

    fun getListTVShowBookmark(): DataSource.Factory<Int, MovieEntity> {
        return movieBookmarkDao.getAllBookmarkTVShow()
    }

}


