package com.android.themoviedb.presentation.state

sealed class UiEvent {

    data class NavigateToDetailScreen(val movieId: String) : UiEvent()
}