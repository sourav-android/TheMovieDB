package com.android.themoviedb.data.remote.datasource.remote

import com.android.themoviedb.data.api.MovieService
import com.android.themoviedb.data.remote.dto.movie.MovieDto
import com.android.themoviedb.data.remote.dto.movie.MovieResponse

class MovieRemoteDataSourceImpl(
    private val movieService: MovieService,
) : MovieRemoteDataSource {

    override suspend fun getMovies(
        apiKey: String,
        pageNumber: Int,
    ): MovieResponse<List<MovieDto>> {
        return movieService.getMovies(apiKey = apiKey, pageNumber = pageNumber)
    }


}