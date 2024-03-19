package com.android.themoviedb.presentation.screens.moviedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.themoviedb.presentation.screens.movie.MovieViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailsScreen(
    modifier : Modifier = Modifier,
    movieId: String,
    navController: NavController,
    movieViewModel: MovieViewModel = koinViewModel<MovieViewModel>(),

) {

    Box(modifier = modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text = "Movie Details Screen",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}