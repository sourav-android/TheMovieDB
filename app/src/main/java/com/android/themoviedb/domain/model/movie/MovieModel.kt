package com.android.themoviedb.domain.model.movie

import java.io.Serializable

data class MovieModel(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
) : Serializable