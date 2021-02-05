package com.lofrus.themoviedb.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(base_url: String) {

    val instanceListMovies: TheMovieDBGetListMovies by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(TheMovieDBGetListMovies::class.java)
    }

    val instanceListTVShow: TheMovieDBGetListTVShow by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(TheMovieDBGetListTVShow::class.java)
    }

    val instanceMoviesDetail: TheMovieDBGetMovieDetail by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(TheMovieDBGetMovieDetail::class.java)
    }

    val instanceTVShowDetail: TheMovieDBGetTVShowDetail by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(TheMovieDBGetTVShowDetail::class.java)
    }

}