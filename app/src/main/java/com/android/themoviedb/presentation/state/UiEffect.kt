package com.android.themoviedb.presentation.state

sealed class UiEffect {
    data class ShowSnackBar(val msg: String) : UiEffect()

}