package com.lofrus.themoviedb.data

import android.content.Context
import com.lofrus.themoviedb.room.MovieBookmarkDatabase
import com.lofrus.themoviedb.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): TheMovieDBRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(MovieBookmarkDatabase.getAppDatabase(context)!!.movieBookmarkDao())
        val appExecutors = AppExecutors()
        return TheMovieDBRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}
