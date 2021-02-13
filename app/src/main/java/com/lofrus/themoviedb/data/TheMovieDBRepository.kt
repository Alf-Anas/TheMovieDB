package com.lofrus.themoviedb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
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

    private val initialLoadSize = 4
    private val loadSize = 4

    override fun getListMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity> , List<MovieEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(initialLoadSize)
                    .setPageSize(loadSize)
                    .build()
                return LivePagedListBuilder(localDataSource.getListMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
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

    override fun getListTVShow(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity> , List<MovieEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(initialLoadSize)
                    .setPageSize(loadSize)
                    .build()
                return LivePagedListBuilder(localDataSource.getListTVShow(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
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

    override fun getListMoviesBookmark(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(initialLoadSize)
            .setPageSize(loadSize)
            .build()
        return LivePagedListBuilder(localDataSource.getListMoviesBookmark(), config).build()
    }

    override fun getListTVShowBookmark(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(initialLoadSize)
            .setPageSize(loadSize)
            .build()
        return LivePagedListBuilder(localDataSource.getListTVShowBookmark(), config).build()
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