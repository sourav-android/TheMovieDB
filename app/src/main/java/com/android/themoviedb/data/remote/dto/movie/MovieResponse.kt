package com.android.themoviedb.data.remote.dto.movie


import com.google.gson.annotations.SerializedName

class MovieResponse<T : Any?> {

    @SerializedName("results")
    val results: T? = null

    @SerializedName("page")
    val page: Int? = null

    @SerializedName("total_pages")
    val totalPages: Int? = null

    @SerializedName("total_results")
    val totalResults: Int? = null
}
