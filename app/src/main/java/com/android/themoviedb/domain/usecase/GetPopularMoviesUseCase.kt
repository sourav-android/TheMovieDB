package com.android.themoviedb.domain.usecase

import com.android.themoviedb.domain.repository.MovieRepository


class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) {
    operator fun invoke() = movieRepository.getPopularMovies()
}