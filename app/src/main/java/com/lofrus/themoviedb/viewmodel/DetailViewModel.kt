package com.lofrus.themoviedb.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.lofrus.themoviedb.BuildConfig
import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.retrofit.RetrofitClient
import com.lofrus.themoviedb.utils.DataDummy
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    val detailMovie = MutableLiveData<DetailMovieEntity>()
    var statusError = MutableLiveData<String?>()
    private val baseURL = "https://api.themoviedb.org/3/"
    private val authToken = BuildConfig.TheMovieDBToken

    fun setMoviesDetail(movie_id: Int) {
        RetrofitClient(baseURL).instanceMoviesDetail.getMovieDetail(movie_id, authToken, "en-US")
            .enqueue(
                object : Callback<JsonElement> {
                    override fun onResponse(
                        call: Call<JsonElement>,
                        response: Response<JsonElement>
                    ) {
                        if (response.isSuccessful) {
                            try {
                                val jsonObject = JSONObject(response.body().toString())
                                val detailMovieEntity = DetailMovieEntity()

                                detailMovieEntity.id = jsonObject.getInt("id")
                                detailMovieEntity.title = jsonObject.getString("title")
                                detailMovieEntity.date = jsonObject.getString("release_date")
                                val genreArray = jsonObject.getJSONArray("genres")
                                var genreString = ""
                                for (i in 0 until genreArray.length()) {
                                    val genreObject = genreArray.getJSONObject(i)
                                    if (i == 0) {
                                        genreString = genreObject.getString("name")
                                    } else {
                                        genreString += ", " + genreObject.getString("name")
                                    }
                                }
                                detailMovieEntity.genre = genreString
                                detailMovieEntity.rating = jsonObject.getDouble("vote_average")
                                detailMovieEntity.poster = "https://www.themoviedb.org/t/p/w220_and_h330_face" + jsonObject.getString("poster_path")
                                detailMovieEntity.backdrop =
                                    "https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces" + jsonObject.getString("backdrop_path")
                                detailMovieEntity.overview = jsonObject.getString("overview")
                                detailMovieEntity.link = jsonObject.getString("homepage")

                                detailMovie.postValue(detailMovieEntity)
                            } catch (e: JSONException) {
                                Log.d("Exception", e.message.toString())
                                statusError.value = "Exception - " + e.message.toString()
                            }
                        } else {
                            Log.d("response.isFailed", "Response Not Successful")
                            statusError.value = "response.isFailed - Response Not Successful"
                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d("onFailure", t.message.toString())
                        statusError.value = "onFailure - " + t.message.toString()
                    }
                })
    }


    fun setTVShowDetail(movie_id: Int) {
        RetrofitClient(baseURL).instanceTVShowDetail.getTVShowDetail(movie_id, authToken, "en-US")
            .enqueue(
                object : Callback<JsonElement> {
                    override fun onResponse(
                        call: Call<JsonElement>,
                        response: Response<JsonElement>
                    ) {
                        if (response.isSuccessful) {
                            try {
                                val jsonObject = JSONObject(response.body().toString())
                                val detailMovieEntity = DetailMovieEntity()

                                detailMovieEntity.id = jsonObject.getInt("id")
                                detailMovieEntity.title = jsonObject.getString("name")
                                detailMovieEntity.date = jsonObject.getString("first_air_date")
                                val genreArray = jsonObject.getJSONArray("genres")
                                var genreString = ""
                                for (i in 0 until genreArray.length()) {
                                    val genreObject = genreArray.getJSONObject(i)
                                    if (i == 0) {
                                        genreString = genreObject.getString("name")
                                    } else {
                                        genreString += ", " + genreObject.getString("name")
                                    }
                                }
                                detailMovieEntity.genre = genreString
                                detailMovieEntity.rating = jsonObject.getDouble("vote_average")
                                detailMovieEntity.poster = "https://www.themoviedb.org/t/p/w220_and_h330_face" + jsonObject.getString("poster_path")
                                detailMovieEntity.backdrop =
                                    "https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces" + jsonObject.getString("backdrop_path")
                                detailMovieEntity.overview = jsonObject.getString("overview")
                                detailMovieEntity.link = jsonObject.getString("homepage")

                                detailMovie.postValue(detailMovieEntity)
                            } catch (e: JSONException) {
                                Log.d("Exception", e.message.toString())
                                statusError.value = "Exception - " + e.message.toString()
                            }
                        } else {
                            Log.d("response.isFailed", "Response Not Successful")
                            statusError.value = "response.isFailed - Response Not Successful"
                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d("onFailure", t.message.toString())
                        statusError.value = "onFailure - " + t.message.toString()
                    }
                })
    }

    fun getMovieDetail(): LiveData<DetailMovieEntity> {
        return detailMovie
    }

    // Dummmy For Unit Testing

    private lateinit var movieEntityDetail: DetailMovieEntity

    fun setMoviesDetailDummy(detailMovie: DetailMovieEntity) {
        this.movieEntityDetail = detailMovie
    }

    fun setTVShowDetailDummy(detailMovie: DetailMovieEntity) {
        this.movieEntityDetail = detailMovie
    }

    fun getMovieDetailDummy(movieID: Int): DetailMovieEntity {
        return if (movieID == 0) DataDummy.generateDummyMovieDetail(0) else if (movieID == 1) DataDummy.generateDummyMovieDetail(0) else movieEntityDetail
    }

}