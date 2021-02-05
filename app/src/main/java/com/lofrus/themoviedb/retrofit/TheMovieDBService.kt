package com.lofrus.themoviedb.retrofit

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBGetListMovies {
    @GET("movie/now_playing")
    fun getListMovies(
        @Query("api_key") api_key: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Call<JsonElement>
}

interface TheMovieDBGetListTVShow {
    @GET("tv/airing_today")
    fun getListTVShow(
        @Query("api_key") api_key: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Call<JsonElement>
}

interface TheMovieDBGetMovieDetail {//movie/{movie_id}?api_key=<<api_key>>&language=en-US
    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movie_id: Int?,
        @Query("api_key") api_key: String?,
        @Query("language") language: String?
    ): Call<JsonElement>
}

interface TheMovieDBGetTVShowDetail { //tv/{tv_id}?api_key=<<api_key>>&language=en-US
    @GET("tv/{tv_id}")
    fun getTVShowDetail(
        @Path("tv_id") tv_id: Int?,
        @Query("api_key") api_key: String?,
        @Query("language") language: String?
    ): Call<JsonElement>
}
