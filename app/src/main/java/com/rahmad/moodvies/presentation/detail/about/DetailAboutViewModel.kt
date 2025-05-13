package com.rahmad.moodvies.presentation.detail.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rahmad.moodvies.domain.usecase.movie.GetMovieDetailsUseCase
import com.rahmad.moodvies.domain.usecase.movie.GetMovieTrailersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailAboutViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieTrailersUseCase: GetMovieTrailersUseCase
) : ViewModel() {

    fun getMovieDetails(movieId: Int) = getMovieDetailsUseCase(movieId).asLiveData()

    fun getMovieTrailers(movieId: Int) = getMovieTrailersUseCase(movieId).asLiveData()
}