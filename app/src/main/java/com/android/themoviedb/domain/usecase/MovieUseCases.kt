package com.android.themoviedb.domain.usecase


data class MovieUseCases(
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getMoviesFromDBUseCase: GetMoviesFromDBUseCase,
)
