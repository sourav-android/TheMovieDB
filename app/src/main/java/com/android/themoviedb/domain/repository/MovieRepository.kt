package com.android.themoviedb.domain.repository

import androidx.paging.PagingData
import com.android.themoviedb.data.local.MovieEntity
import com.android.themoviedb.pagingonly.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    /*-- for Remote ---*/
    /*fun getMovies() : Flow<Resource<List<MovieDto>>>?*/
    fun getMovieList() : Flow<PagingData<MovieModel>>
    fun getMoviesFromLocal() :Flow<PagingData<MovieEntity>>

    /*--- for Local ---*/

    /*
    suspend fun saveMoviesToDB(movieEntity: MovieEntity)
    fun getSavedMoviesFromDB() : PagingSource<Int, MovieEntity>

    suspend  fun clearAll()
    */

}