package com.android.themoviedb.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.themoviedb.presentation.screens.ArtistScreen
import com.android.themoviedb.presentation.screens.movie.MovieScreen
import com.android.themoviedb.presentation.screens.movie.MovieViewModel
import com.android.themoviedb.presentation.screens.moviedetails.MovieDetailsScreen
import com.android.themoviedb.presentation.utils.ScreenConstants
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()


    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                listOfNavItems.forEach { navItems ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route == navItems.route
                        } == true,
                        onClick = {
                            navController.navigate(
                                navItems.route
                            ) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = navItems.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = navItems.label)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Movie.route,
            modifier = Modifier
                .padding(paddingValues)
        ) {


            composable(route = Screen.Movie.route) {
                val movieViewModel = koinViewModel<MovieViewModel>()
                val allMovies = movieViewModel.getAllPopularMovies.collectAsLazyPagingItems()

                MovieScreen(
                    allMovies = allMovies,
                    navController = navController
                )

            }

            composable(
                route = Screen.MovieDetails.route,
                arguments = listOf(navArgument(ScreenConstants.MOVIE_DETAILS_ARGUMENT_KEY) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString(ScreenConstants.MOVIE_DETAILS_ARGUMENT_KEY)
                    ?.let { MovieDetailsScreen(modifier = Modifier, it, navController) }
            }
            composable(route = Screen.Artist.route) {
                ArtistScreen()
            }
        }
    }


}