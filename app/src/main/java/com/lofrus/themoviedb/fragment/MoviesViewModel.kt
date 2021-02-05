package com.lofrus.themoviedb.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.lofrus.themoviedb.BuildConfig
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.retrofit.RetrofitClient
import com.lofrus.themoviedb.utils.DataDummy
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {

    val listMovie = MutableLiveData<ArrayList<MovieEntity>>()
    var statusError = MutableLiveData<String?>()
    private val baseURL = "https://api.themoviedb.org/3/"
    private val authToken = BuildConfig.TheMovieDBToken

    fun setListMovies() {
        RetrofitClient(baseURL).instanceListMovies.getListMovies(authToken, "en-US", 1)
            .enqueue(
                object : Callback<JsonElement> {
                    override fun onResponse(
                        call: Call<JsonElement>,
                        response: Response<JsonElement>
                    ) {
                        if (response.isSuccessful) {
                            try {
                                val jsonObject = JSONObject(response.body().toString())
                                val jsonArrayItem: JSONArray = jsonObject.getJSONArray("results")

                                val list = arrayListOf<MovieEntity>()
                                for (i in 0 until jsonArrayItem.length()) {
                                    val dataJSON = jsonArrayItem.getJSONObject(i)
                                    val movieEntity = MovieEntity()
                                    movieEntity.id = dataJSON.getInt("id")
                                    movieEntity.type = 0
                                    movieEntity.title = dataJSON.getString("title")
                                    movieEntity.date = dataJSON.getString("release_date")
                                    movieEntity.rating = dataJSON.getDouble("vote_average")
                                    movieEntity.poster = "https://www.themoviedb.org/t/p/w220_and_h330_face" + dataJSON.getString("poster_path")
                                    list.add(movieEntity)
                                }
                                listMovie.postValue(list)
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


    fun setListTVShow() {
        RetrofitClient(baseURL).instanceListTVShow.getListTVShow(authToken, "en-US", 1)
            .enqueue(
                object : Callback<JsonElement> {
                    override fun onResponse(
                        call: Call<JsonElement>,
                        response: Response<JsonElement>
                    ) {
                        if (response.isSuccessful) {
                            try {
                                val jsonObject = JSONObject(response.body().toString())
                                val jsonArrayItem: JSONArray = jsonObject.getJSONArray("results")

                                val list = arrayListOf<MovieEntity>()
                                for (i in 0 until jsonArrayItem.length()) {
                                    val dataJSON = jsonArrayItem.getJSONObject(i)
                                    val movieEntity = MovieEntity()
                                    movieEntity.id = dataJSON.getInt("id")
                                    movieEntity.type = 1
                                    movieEntity.title = dataJSON.getString("name")
                                    movieEntity.date = dataJSON.getString("first_air_date")
                                    movieEntity.rating = dataJSON.getDouble("vote_average")
                                    movieEntity.poster = "https://www.themoviedb.org/t/p/w220_and_h330_face" + dataJSON.getString("poster_path")
                                    list.add(movieEntity)
                                }
                                listMovie.postValue(list)
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

    fun getListMovie(): LiveData<ArrayList<MovieEntity>> {
        return listMovie
    }

    // Dummy For Unit Testing

    fun getListMoviesDummy(): List<MovieEntity> = DataDummy.generateDummyListMovies()

    fun getListTVShowDummy(): List<MovieEntity> = DataDummy.generateDummyListTVShow()


}