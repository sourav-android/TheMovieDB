package com.android.themoviedb.data.repository.datasourceImpl

import com.android.themoviedb.data.local.MovieDao
import com.android.themoviedb.data.repository.datasource.remote.MovieLocalDataSource
import com.android.themoviedb.domain.model.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSourceImpl(private val movieDao: MovieDao) : MovieLocalDataSource {
    override fun getMoviesFromDB(movieId: Int): Flow<MovieEntity>  =   movieDao.getMovie(movieId)
}