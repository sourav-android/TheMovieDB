package com.android.themoviedb.domain.usecase

import com.android.themoviedb.domain.repository.MovieRepository

class SaveMoviesUseCase(
    private val movieRepository: MovieRepository,
) {

    /*suspend operator fun invoke(movieEntity: PagingData<MovieEntity>) = movieRepository.saveMoviesToDB(movieEntity)*/
}