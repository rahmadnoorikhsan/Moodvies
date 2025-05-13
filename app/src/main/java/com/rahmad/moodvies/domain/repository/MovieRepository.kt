package com.rahmad.moodvies.domain.repository

import androidx.paging.PagingData
import com.rahmad.moodvies.data.repository.Result
import com.rahmad.moodvies.domain.model.DetailMovie
import com.rahmad.moodvies.domain.model.Genre
import com.rahmad.moodvies.domain.model.Movie
import com.rahmad.moodvies.domain.model.Review
import com.rahmad.moodvies.domain.model.Trailer
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<Result<List<Movie>>>

    fun getAllGenres(): Flow<Result<List<Genre>>>

    fun getMoviesByGenre(genresId: String): Flow<PagingData<Movie>>

    fun getMovieDetails(movieId: Int): Flow<Result<DetailMovie>>

    fun getMovieTrailers(movieId: Int): Flow<Result<List<Trailer>>>

    fun getMovieReviews(movieId: Int): Flow<PagingData<Review>>
}