package com.rahmad.moodvies.domain.usecase.movie

import com.rahmad.moodvies.data.repository.Result
import com.rahmad.moodvies.domain.model.Genre
import com.rahmad.moodvies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Result<List<Genre>>> {
        return repository.getAllGenres()
    }
}