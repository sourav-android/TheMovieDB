package com.android.themoviedb.domain.usecase

import com.android.themoviedb.domain.repository.MovieRepository

class GetSavedMoviesUseCase(
    private val movieRepository: MovieRepository
) {

    /*operator fun invoke() = movieRepository.getMovieList()*/
}