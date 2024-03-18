package com.android.themoviedb.data.repository.datasource.remote

import androidx.paging.PagingData
import com.android.themoviedb.domain.model.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    fun getPopularMovies(): Flow<PagingData<MovieEntity>>
}