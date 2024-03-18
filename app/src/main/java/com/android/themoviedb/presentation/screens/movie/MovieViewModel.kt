package com.android.themoviedb.presentation.screens.movie

import androidx.lifecycle.ViewModel
import com.android.themoviedb.domain.usecase.MovieUseCases

class MovieViewModel(
    movieUseCases: MovieUseCases,
) : ViewModel() {

    val getAllPopularMovies = movieUseCases.getPopularMoviesUseCase()
}

