package com.android.themoviedb.data.repository.datasource.remote

import com.android.themoviedb.domain.model.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    fun getMoviesFromDB(movieId : Int): Flow<MovieEntity>
}