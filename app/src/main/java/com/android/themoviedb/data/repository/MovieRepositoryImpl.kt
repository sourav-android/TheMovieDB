package com.android.themoviedb.data.repository

import com.android.themoviedb.data.repository.datasource.remote.MovieLocalDataSource
import com.android.themoviedb.data.repository.datasource.remote.MovieRemoteDataSource
import com.android.themoviedb.domain.model.movie.MovieEntity
import com.android.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
) :
    MovieRepository {
    override fun getPopularMovies() =
        movieRemoteDataSource.getPopularMovies()

    override fun getMoviesFromDB(movieId: Int): Flow<MovieEntity> =
        movieLocalDataSource.getMoviesFromDB(movieId)
}