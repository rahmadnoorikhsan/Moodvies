package com.rahmad.moodvies.domain.model

data class DetailMovie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val runtime: Int?,
    val budget: Int?,
    val revenue: Int?,
    val tagline: String?,
    val imdbId: String?,
    val homepage: String?,
    val status: String?,
    val originalLanguage: String?,
    val genres: List<Genre>,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val spokenLanguages: List<SpokenLanguage>
)