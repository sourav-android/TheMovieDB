package com.android.themoviedb.presentation.navigation

/*
enum  class Screens {
    MovieScreen,
    ArtistScreen
}*/

sealed class Screen(val route : String){
    data object Movie : Screen("movie_screen")
    data object Artist : Screen("artist_screen")

    data object MovieDetails : Screen("movie_details_screen/{movieId}") {
        fun movieId(movieId: String) = "movie_details_screen/$movieId"
    }
}