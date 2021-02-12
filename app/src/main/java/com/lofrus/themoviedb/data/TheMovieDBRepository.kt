package com.lofrus.themoviedb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.retrofit.ApiResponse
import com.lofrus.themoviedb.room.MovieBookmarkEntity
import com.lofrus.themoviedb.utils.AppExecutors
import com.lofrus.themoviedb.vo.Resource

class TheMovieDBRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    TheMovieDBDataSource {

    companion object {
        @Volatile
        private var instance: TheMovieDBRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors): TheMovieDBRepository =
            instance ?: synchronized(this) {
                instance ?: TheMovieDBRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getListMovies(): LiveData<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity> , List<MovieEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> {
                return localDataSource.getListMovies()
            }

            override fun shouldFetch(data: List<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieEntity>>> {
                return remoteDataSource.getListMovies()
            }

            override fun saveCallResult(data: List<MovieEntity>) {
                val movieList = ArrayList<MovieBookmarkEntity>()
                for (response in data) {
                    val movieEntity = MovieBookmarkEntity(response.id,
                        response.type,
                        response.title,
                        response.date,
                        response.rating,
                        response.poster)
                    movieList.add(movieEntity)
                }
                localDataSource.insertMovieToLocal(movieList)
            }
        }.asLiveData()
    }

    override fun getListTVShow(): LiveData<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity> , List<MovieEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> {
                return localDataSource.getListTVShow()
            }

            override fun shouldFetch(data: List<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieEntity>>> {
                return remoteDataSource.getListTVShow()
            }

            override fun saveCallResult(data: List<MovieEntity>) {
                val movieList = ArrayList<MovieBookmarkEntity>()
                for (response in data) {
                    val movieEntity = MovieBookmarkEntity(response.id,
                        response.type,
                        response.title,
                        response.date,
                        response.rating,
                        response.poster)
                    movieList.add(movieEntity)
                }
                localDataSource.insertMovieToLocal(movieList)
            }
        }.asLiveData()
    }

    override fun getListMoviesBookmark(): LiveData<List<MovieEntity>> {
        return localDataSource.getListMoviesBookmark()
    }

    override fun getListTVShowBookmark(): LiveData<List<MovieEntity>> {
        return localDataSource.getListTVShowBookmark()
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