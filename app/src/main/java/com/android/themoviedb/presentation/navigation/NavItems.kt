package com.android.themoviedb.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItems(
    val label : String,
    val icon : ImageVector,
    val route : String
)

val listOfNavItems = listOf(
    NavItems(
        label = "Movie",
        icon = Icons.Default.Home,
        route = Screen.Movie.route
    ),
    NavItems(
        label = "Artist",
        icon = Icons.Default.Person,
        route = Screen.Artist.route
    ),
)
