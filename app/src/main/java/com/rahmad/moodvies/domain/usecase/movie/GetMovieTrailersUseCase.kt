package com.rahmad.moodvies.domain.usecase.movie

import com.rahmad.moodvies.data.repository.Result
import com.rahmad.moodvies.domain.model.Trailer
import com.rahmad.moodvies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieTrailersUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int) : Flow<Result<List<Trailer>>> {
        return repository.getMovieTrailers(movieId)
    }
}