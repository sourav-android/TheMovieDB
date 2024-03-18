package com.android.themoviedb.data.api

import com.android.themoviedb.BuildConfig
import com.android.themoviedb.data.remote.dto.movie.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
        @Query("page") pageNumber: Int = 1,
    ): Response<MovieResponse>

}