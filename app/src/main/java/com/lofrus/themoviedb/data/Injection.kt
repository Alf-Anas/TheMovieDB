package com.lofrus.themoviedb.data

object Injection {
    fun provideRepository(): TheMovieDBRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return TheMovieDBRepository.getInstance(remoteDataSource)
    }
}
