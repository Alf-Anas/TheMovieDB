package com.lofrus.themoviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lofrus.themoviedb.data.Injection
import com.lofrus.themoviedb.data.TheMovieDBRepository
import com.lofrus.themoviedb.fragment.MoviesViewModel

class ViewModelFactory private constructor(private val theMovieDBRepository: TheMovieDBRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideRepository())
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                MoviesViewModel(theMovieDBRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(theMovieDBRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
