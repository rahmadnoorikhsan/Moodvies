package com.rahmad.moodvies.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.rahmad.moodvies.data.remote.response.genres.GenreItem
import com.rahmad.moodvies.data.remote.response.images.LogosItem
import com.rahmad.moodvies.data.remote.response.movies.DetailMovieResponse
import com.rahmad.moodvies.data.remote.response.movies.MovieItem
import com.rahmad.moodvies.data.remote.response.movies.ProductionCompaniesItem
import com.rahmad.moodvies.data.remote.response.movies.ProductionCountriesItem
import com.rahmad.moodvies.data.remote.response.movies.SpokenLanguagesItem
import com.rahmad.moodvies.data.remote.response.review.ReviewItem
import com.rahmad.moodvies.data.remote.response.videos.VideoItem
import com.rahmad.moodvies.domain.model.DetailMovie
import com.rahmad.moodvies.domain.model.Genre
import com.rahmad.moodvies.domain.model.Logo
import com.rahmad.moodvies.domain.model.Movie
import com.rahmad.moodvies.domain.model.ProductionCompany
import com.rahmad.moodvies.domain.model.ProductionCountry
import com.rahmad.moodvies.domain.model.Review
import com.rahmad.moodvies.domain.model.SpokenLanguage
import com.rahmad.moodvies.domain.model.Trailer

object DataMapper {

    fun MovieItem.toDomain() = Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        originalLanguage = originalLanguage,
        popularity = popularity,
        genreIds = genreIds
    )

    fun LogosItem.toDomain() = Logo(
        filePath = filePath
    )

    fun GenreItem.toDomain() = Genre(
        id = id,
        genreName = genreName
    )

    fun ProductionCompaniesItem.toDomain() = ProductionCompany(
        id = id,
        logoPath = logoPath,
        name = name,
        originCountry = originCountry
    )

    fun ProductionCountriesItem.toDomain() = ProductionCountry(
        iso31661 = iso31661,
        name = name
    )

    fun SpokenLanguagesItem.toDomain() = SpokenLanguage(
        englishName = englishName,
        iso6391 = iso6391,
        name = name
    )

    fun mapMoviePagingResponseToMoviePagingDomain(
        input: PagingData<MovieItem>
    ): PagingData<Movie> =
        input.map {
            Movie(
                it.id,
                it.overview,
                it.backdropPath,
                it.originalLanguage,
                it.releaseDate,
                it.popularity,
                it.voteAverage,
                it.title,
                it.genreIds,
                it.voteCount,
                it.posterPath
            )
        }

    fun mapReviewPagingResponseToReviewPagingDomain(
        input: PagingData<ReviewItem>
    ): PagingData<Review> =
        input.map {
            Review(
                it.id,
                it.author,
                it.content,
                it.updatedAt
            )
        }

    fun DetailMovieResponse.toDomain() = DetailMovie(
        id = id,
        title = title.orEmpty(),
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        popularity = popularity ?: 0.0,
        runtime = runtime,
        budget = budget,
        revenue = revenue,
        tagline = tagline,
        imdbId = imdbId,
        homepage = homepage,
        status = status,
        originalLanguage = originalLanguage,
        genres = genres.map { genres ->
            genres.toDomain()
        },
        productionCompanies = productionCompanies.map { productionCompanies ->
            productionCompanies.toDomain()
        },
        productionCountries = productionCountries.map { productionCountries ->
            productionCountries.toDomain()
        },
        spokenLanguages = spokenLanguages.map { spokenLanguages ->
            spokenLanguages.toDomain()
        }
    )

    fun VideoItem.toDomain() = Trailer(
        key = key,
        name = name,
        site = site,
        type = type
    )
}