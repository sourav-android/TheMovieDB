package com.android.themoviedb.domain.usecase

import androidx.paging.PagingData
import com.android.themoviedb.pagingonly.MovieModel
import com.android.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(
    private val movieRepository: MovieRepository,
) {

    /*operator fun invoke() = movieRepository.getMovies()*/
    /*operator fun invoke(): Flow<PagingData<MovieModel>> {
        return movieRepository.getMovieList()
    }*/

    operator fun invoke() = movieRepository.getMoviesFromLocal()
}