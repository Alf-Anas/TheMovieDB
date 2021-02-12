package com.lofrus.themoviedb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.room.MovieBookmarkDao
import com.lofrus.themoviedb.room.MovieBookmarkEntity
import kotlin.concurrent.thread


class LocalDataSource private constructor(private val movieBookmarkDao: MovieBookmarkDao) {

    companion object {
        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieBookmarkDao: MovieBookmarkDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieBookmarkDao)
    }

    private fun toMovieEntity(movieBookmarkEntity: MovieBookmarkEntity) : MovieEntity{
        return MovieEntity(
            movieBookmarkEntity.id,
            movieBookmarkEntity.type,
            movieBookmarkEntity.title,
            movieBookmarkEntity.date,
            movieBookmarkEntity.rating,
            movieBookmarkEntity.poster
        )
    }

    fun getListMovies(): LiveData<List<MovieEntity>> {
        val listMovie = MutableLiveData<List<MovieEntity>>()
        thread {
            val moviesBookmark = movieBookmarkDao.getAllLocalMovies()
            val list = arrayListOf<MovieEntity>()
            for (i in moviesBookmark.indices) {
                list.add(toMovieEntity(moviesBookmark[i]))
            }
            listMovie.postValue(list)
        }
        return listMovie
    }

    fun getListTVShow(): LiveData<List<MovieEntity>> {
        val listMovie = MutableLiveData<List<MovieEntity>>()
        thread {
            val moviesBookmark = movieBookmarkDao.getAllLocalTVShow()
            val list = arrayListOf<MovieEntity>()
            for (i in moviesBookmark.indices) {
                list.add(toMovieEntity(moviesBookmark[i]))
            }
            listMovie.postValue(list)
        }
        return listMovie
    }

    fun insertMovieToLocal(tempListMovie: List<MovieBookmarkEntity>) = movieBookmarkDao.insertMovieToLocal(tempListMovie)

    fun getListMoviesBookmark(): LiveData<List<MovieEntity>> {
        val listMovie = MutableLiveData<List<MovieEntity>>()
        thread {
            val moviesBookmark = movieBookmarkDao.getAllBookmarkMovies()
            val list = arrayListOf<MovieEntity>()
            for (i in moviesBookmark.indices) {
                list.add(toMovieEntity(moviesBookmark[i]))
            }
            listMovie.postValue(list)
        }
        return listMovie
    }

    fun getListTVShowBookmark(): LiveData<List<MovieEntity>> {
        val listMovie = MutableLiveData<List<MovieEntity>>()
        thread {
            val moviesBookmark = movieBookmarkDao.getAllBookmarkTVShow()
            val list = arrayListOf<MovieEntity>()
            for (i in moviesBookmark.indices) {
                list.add(toMovieEntity(moviesBookmark[i]))
            }
            listMovie.postValue(list)
        }
        return listMovie
    }

}


