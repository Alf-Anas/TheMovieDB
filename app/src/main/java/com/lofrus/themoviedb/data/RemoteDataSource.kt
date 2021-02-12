package com.lofrus.themoviedb.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.lofrus.themoviedb.BuildConfig
import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.retrofit.ApiResponse
import com.lofrus.themoviedb.retrofit.ResponseListMovie
import com.lofrus.themoviedb.retrofit.ResultsItemListMovie
import com.lofrus.themoviedb.retrofit.RetrofitClient
import com.lofrus.themoviedb.utils.DataDummy
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor() {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    val listMovie = MutableLiveData<ApiResponse<List<MovieEntity>>>()
    val detailMovie = MutableLiveData<DetailMovieEntity>()
    var statusError = MutableLiveData<String?>()
    private val baseURL = BuildConfig.BaseURL
    private val posterBaseURL = BuildConfig.PosterBaseURL
    private val backdropBaseURL = BuildConfig.BackdropBaseURL
    private val authToken = BuildConfig.TheMovieDBToken

    fun ResultsItemListMovie.toMovieEntity() = MovieEntity(
        id = id,
        type = type,
        title = title,
        date = releaseDate,
        rating = voteAverage,
        poster = "$posterBaseURL$posterPath"
    )

    fun getListMovies(): LiveData<ApiResponse<List<MovieEntity>>> {
        RetrofitClient(baseURL).instanceListMovies.getListMovies(authToken, "en-US", 1)
            .enqueue(
                object : Callback<ResponseListMovie> {
                    override fun onResponse(
                        call: Call<ResponseListMovie>,
                        response: Response<ResponseListMovie>
                    ) {
                        if (response.isSuccessful) {
                            val results = response.body()?.results
                            val list = arrayListOf<MovieEntity>()
                            for (i in results?.indices!!) {
                                list.add(results[i].toMovieEntity())
                            }
                            listMovie.value = ApiResponse.success(list)
                        } else {
                            Log.d("response.isFailed", "Response Not Successful")
                            val emptyList = arrayListOf<MovieEntity>()
                            listMovie.value = ApiResponse.error("response.isFailed - Response Not Successful", emptyList)
                        }
                    }

                    override fun onFailure(call: Call<ResponseListMovie>, t: Throwable) {
                        Log.d("onFailure", t.message.toString())
                        val emptyList = arrayListOf<MovieEntity>()
                        listMovie.value = ApiResponse.error("onFailure - " + t.message.toString(), emptyList)
                    }
                })
        return listMovie
    }

    fun getListTVShow(): LiveData<ApiResponse<List<MovieEntity>>> {
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
                                    movieEntity.poster = posterBaseURL + dataJSON.getString("poster_path")
                                    list.add(movieEntity)
                                }
                                listMovie.value = ApiResponse.success(list)
                            } catch (e: JSONException) {
                                Log.d("Exception", e.message.toString())
                                val emptyList = arrayListOf<MovieEntity>()
                                listMovie.value = ApiResponse.error("Exception - " + e.message.toString(), emptyList)
                            }
                        } else {
                            Log.d("response.isFailed", "Response Not Successful")
                            val emptyList = arrayListOf<MovieEntity>()
                            listMovie.value = ApiResponse.error("response.isFailed - Response Not Successful", emptyList)
                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d("onFailure", t.message.toString())
                        val emptyList = arrayListOf<MovieEntity>()
                        listMovie.value = ApiResponse.error("onFailure - " + t.message.toString(), emptyList)
                    }
                })
        return listMovie
    }

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
                                detailMovieEntity.poster = posterBaseURL + jsonObject.getString("poster_path")
                                detailMovieEntity.backdrop = backdropBaseURL + jsonObject.getString("backdrop_path")
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
                                detailMovieEntity.poster = posterBaseURL + jsonObject.getString("poster_path")
                                detailMovieEntity.backdrop = backdropBaseURL + jsonObject.getString("backdrop_path")
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

    //For Dummy Unit Testing
    fun getListMoviesDummy(): List<MovieEntity> = DataDummy.generateDummyListMovies()

    fun getListTVShowDummy(): List<MovieEntity> = DataDummy.generateDummyListTVShow()

    private lateinit var movieEntityDetail: DetailMovieEntity

    fun setMoviesDetailDummy(detailMovie: DetailMovieEntity) {
        this.movieEntityDetail = detailMovie
    }

    fun setTVShowDetailDummy(detailMovie: DetailMovieEntity) {
        this.movieEntityDetail = detailMovie
    }

    fun getMovieDetailDummy(movieID: Int): DetailMovieEntity {
        return if (movieID == 0) DataDummy.generateDummyMovieDetail(0) else DataDummy.generateDummyMovieDetail(1)
    }
}

