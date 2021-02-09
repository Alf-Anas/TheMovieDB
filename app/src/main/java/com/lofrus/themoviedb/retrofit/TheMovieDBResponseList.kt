package com.lofrus.themoviedb.retrofit

import com.google.gson.annotations.SerializedName

data class ResponseListMovie(

	@field:SerializedName("results")
	val results: ArrayList<ResultsItemListMovie>
)

data class ResultsItemListMovie(

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("id")
	val id: Int,

	val type: Int = 0,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("poster_path")
	val posterPath: String
)
