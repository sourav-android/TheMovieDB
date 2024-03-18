package com.android.themoviedb.presentation.state

import com.android.themoviedb.data.remote.dto.movie.MovieDto

data class UiState(
    val movies: List<MovieDto>? = emptyList(),
    val errorMsg: String? = "",
    val isLoading: Boolean = false
)