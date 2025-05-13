package com.rahmad.moodvies.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rahmad.moodvies.domain.usecase.movie.GetAllGenresUseCase
import com.rahmad.moodvies.domain.usecase.movie.GetMoviesByGenreUseCase
import com.rahmad.moodvies.domain.usecase.movie.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getAllGenresUseCase: GetAllGenresUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase
) : ViewModel() {

    fun getPopularMovies() = getPopularMoviesUseCase().asLiveData()

    fun getAllGenres() = getAllGenresUseCase().asLiveData()

    fun getMoviesByGenre(genreId: String) =
        getMoviesByGenreUseCase(genreId).cachedIn(viewModelScope)
}