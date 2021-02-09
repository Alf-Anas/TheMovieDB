package com.lofrus.themoviedb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lofrus.themoviedb.data.TheMovieDBRepository
import com.lofrus.themoviedb.model.DetailMovieEntity

class DetailViewModel(private val theMovieDBRepository: TheMovieDBRepository) : ViewModel() {

    fun getStatusError() : LiveData<String?>{
        return theMovieDBRepository.getStatusError()
    }

    fun setMoviesDetail(movie_id: Int) {
        theMovieDBRepository.setMoviesDetail(movie_id)
    }

    fun setTVShowDetail(movie_id: Int) {
        theMovieDBRepository.setTVShowDetail(movie_id)
    }

    fun getMovieDetail(): LiveData<DetailMovieEntity> {
        return theMovieDBRepository.getMovieDetail()
    }

    // Dummmy For Unit Testing

    fun setMoviesDetailDummy(detailMovie: DetailMovieEntity) {
        theMovieDBRepository.setMoviesDetailDummy(detailMovie)
    }

    fun setTVShowDetailDummy(detailMovie: DetailMovieEntity) {
        theMovieDBRepository.setTVShowDetailDummy(detailMovie)
    }

    fun getMovieDetailDummy(movieID: Int): DetailMovieEntity {
        return theMovieDBRepository.getMovieDetailDummy(movieID)
    }

}