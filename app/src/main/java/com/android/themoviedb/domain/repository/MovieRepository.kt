package com.android.themoviedb.domain.repository

import androidx.paging.PagingData
import com.android.themoviedb.domain.model.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    /*-- for Remote ---*/
    fun getPopularMovies(): Flow<PagingData<MovieEntity>>

    /*--- for Local ---*/
    fun getMoviesFromDB(movieId: Int): Flow<MovieEntity>

}