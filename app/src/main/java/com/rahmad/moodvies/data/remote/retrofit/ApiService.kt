package com.rahmad.moodvies.data.remote.retrofit

import com.rahmad.moodvies.data.remote.response.genres.GenresResponse
import com.rahmad.moodvies.data.remote.response.images.ImagesResponse
import com.rahmad.moodvies.data.remote.response.movies.DetailMovieResponse
import com.rahmad.moodvies.data.remote.response.movies.MoviesResponse
import com.rahmad.moodvies.data.remote.response.review.ReviewResponse
import com.rahmad.moodvies.data.remote.response.videos.VideosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MoviesResponse

    @GET("movie/{movie_id}/images")
    suspend fun getImageMovie(
        @Path("movie_id") id: Int,
        @Query("include_image_language") imageLanguage: String = "en"
    ): ImagesResponse


    @GET("genre/movie/list")
    suspend fun getAllGenres(): GenresResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres", encoded = true) genresId: String,
        @Query("page") page: Int = 1
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): DetailMovieResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(
        @Path("movie_id") movieId: Int
    ): VideosResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1
    ): ReviewResponse
}