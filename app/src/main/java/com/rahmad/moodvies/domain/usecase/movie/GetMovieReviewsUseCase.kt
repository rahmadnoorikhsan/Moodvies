package com.rahmad.moodvies.domain.usecase.movie

import androidx.paging.PagingData
import com.rahmad.moodvies.domain.model.Review
import com.rahmad.moodvies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<PagingData<Review>> {
        return repository.getMovieReviews(movieId)
    }
}