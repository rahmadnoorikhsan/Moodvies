package com.rahmad.moodvies.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rahmad.moodvies.domain.usecase.movie.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    fun getMovieDetails(movieId: Int) = getMovieDetailUseCase(movieId).asLiveData()
}