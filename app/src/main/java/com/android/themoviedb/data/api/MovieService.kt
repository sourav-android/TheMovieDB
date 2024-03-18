package com.android.themoviedb.data.api


import com.android.themoviedb.data.remote.dto.movie.MovieDto
import com.android.themoviedb.data.remote.dto.movie.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey:  String,
        @Query("page") pageNumber :  Int
    ): MovieResponse<List<MovieDto>>




}