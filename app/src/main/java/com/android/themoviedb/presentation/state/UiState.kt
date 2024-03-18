package com.android.themoviedb.presentation.state

import com.android.themoviedb.domain.model.movie.MovieEntity

data class UiState(
    val movies: List<MovieEntity>? = emptyList(),
    val errorMsg: String? = "",
    val isLoading: Boolean = false
)