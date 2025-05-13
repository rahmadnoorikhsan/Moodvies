package com.rahmad.moodvies.domain.model

data class Movie(
    val id: Int,
    val overview: String? = null,
    val backdropPath: String? = null,
    val originalLanguage: String,
    val releaseDate: String? = null,
    val popularity: Double,
    val voteAverage: Double,
    val title: String,
    val genreIds: List<Int?>?,
    val voteCount: Int,
    val posterPath: String? = null,
    var logo: String? = ""
)