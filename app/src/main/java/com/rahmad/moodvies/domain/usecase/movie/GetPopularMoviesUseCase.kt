package com.rahmad.moodvies.domain.usecase.movie

import com.rahmad.moodvies.data.repository.Result
import com.rahmad.moodvies.domain.model.Movie
import com.rahmad.moodvies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Result<List<Movie>>> {
        return repository.getPopularMovies()
    }
}