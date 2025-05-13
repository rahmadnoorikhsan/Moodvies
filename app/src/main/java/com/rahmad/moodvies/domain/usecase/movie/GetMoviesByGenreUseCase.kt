package com.rahmad.moodvies.domain.usecase.movie

import androidx.paging.PagingData
import com.rahmad.moodvies.domain.model.Movie
import com.rahmad.moodvies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(genresId: String): Flow<PagingData<Movie>> {
        return repository.getMoviesByGenre(genresId)
    }
}