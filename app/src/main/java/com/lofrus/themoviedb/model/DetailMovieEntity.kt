package com.lofrus.themoviedb.model

class DetailMovieEntity(
    var id: Int = 0,
    var title: String = "",
    var date: String = "",
    var genre: String = "",
    var rating: Double? = 0.0,
    var poster: String? = "",
    var backdrop: String? = "",
    var overview: String? = "",
    var link: String? = ""
)