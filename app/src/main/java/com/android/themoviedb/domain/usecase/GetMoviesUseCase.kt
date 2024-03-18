package com.android.themoviedb.domain.usecase

import androidx.paging.PagingData
import com.android.themoviedb.data.local.MovieEntity
import com.android.themoviedb.data.remote.dto.movie.MovieDto
import com.android.themoviedb.domain.model.movie.MovieModel
import com.android.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(
    private val movieRepository: MovieRepository,
) {

    /*operator fun invoke() = movieRepository.getMovies()*/
    operator fun invoke(): Flow<PagingData<MovieModel>> {
        return movieRepository.getMovieList()
    }
}