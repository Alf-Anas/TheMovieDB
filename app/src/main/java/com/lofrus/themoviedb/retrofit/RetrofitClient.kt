package com.lofrus.themoviedb.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(base_url: String) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(base_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instanceListMovies: TheMovieDBGetListMovies by lazy {
        retrofit.create(TheMovieDBGetListMovies::class.java)
    }

    val instanceListTVShow: TheMovieDBGetListTVShow by lazy {
        retrofit.create(TheMovieDBGetListTVShow::class.java)
    }

    val instanceMoviesDetail: TheMovieDBGetMovieDetail by lazy {
        retrofit.create(TheMovieDBGetMovieDetail::class.java)
    }

    val instanceTVShowDetail: TheMovieDBGetTVShowDetail by lazy {
        retrofit.create(TheMovieDBGetTVShowDetail::class.java)
    }

}