package com.rahmad.moodvies.presentation.detail.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rahmad.moodvies.domain.usecase.movie.GetMovieReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailReviewViewModel @Inject constructor(
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel() {

    fun getMovieReviews(movieId: Int) =
        getMovieReviewsUseCase(movieId).cachedIn(viewModelScope)
}