package com.rahmad.moodvies.data.remote

import com.rahmad.moodvies.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getPopularMovies() = apiService.getPopularMovies()

    suspend fun getImageMovie(movieId: Int) = apiService.getImageMovie(movieId)

    suspend fun getAllGenres() = apiService.getAllGenres()

    suspend fun getMoviesByGenre(genresId: String, page: Int) = apiService.getMoviesByGenre(genresId, page)

    suspend fun getMovieDetails(movieId: Int) = apiService.getMovieDetails(movieId)

    suspend fun getMovieTrailers(movieId: Int) = apiService.getMovieTrailers(movieId)

    suspend fun getMovieReviews(movieId: Int, page: Int) = apiService.getMovieReviews(movieId, page)
}