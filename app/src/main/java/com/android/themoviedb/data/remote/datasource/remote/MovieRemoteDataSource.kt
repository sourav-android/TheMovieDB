package com.android.themoviedb.data.remote.datasource.remote

import com.android.themoviedb.data.remote.dto.movie.MovieDto
import com.android.themoviedb.data.remote.dto.movie.MovieResponse

interface MovieRemoteDataSource {
    suspend fun getMovies(
        apiKey: String,
        pageNumber: Int
    ): MovieResponse<List<MovieDto>>

}
