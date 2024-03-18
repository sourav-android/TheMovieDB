package com.android.themoviedb.data.remote.dto.movie

import com.android.themoviedb.domain.model.movie.MovieEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MovieResponse(
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("results")
    val movieEntities: List<MovieEntity>,
) : Serializable

/*class MovieResponse<T : Any?> {

    @SerializedName("results")
    val results: T? = null

    @SerializedName("page")
    val page: Int? = null

    @SerializedName("total_pages")
    val totalPages: Int? = null

    @SerializedName("total_results")
    val totalResults: Int? = null
}*/
