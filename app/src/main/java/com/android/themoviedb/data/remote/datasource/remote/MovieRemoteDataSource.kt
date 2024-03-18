package com.android.themoviedb.data.remote.datasource.remote

import com.android.themoviedb.data.remote.dto.movie.MovieDto
import com.android.themoviedb.data.remote.dto.movie.MovieResponse
import com.android.themoviedb.domain.model.movie.MovieModel

interface MovieRemoteDataSource {
    suspend fun getMovies(
        apiKey: String,
        pageNumber: Int
    ): MovieResponse<List<MovieDto>>

}
