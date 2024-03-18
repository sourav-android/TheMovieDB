package com.android.themoviedb.domain.usecase

import com.android.themoviedb.domain.repository.MovieRepository


class GetMoviesFromDBUseCase(private val movieRepository: MovieRepository) {
    operator fun invoke(movieID: Int) = movieRepository.getMoviesFromDB(movieID)
}