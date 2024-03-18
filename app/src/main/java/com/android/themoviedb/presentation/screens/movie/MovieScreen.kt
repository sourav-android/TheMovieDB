package com.android.themoviedb.presentation.screens.movie

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.android.themoviedb.domain.model.movie.MovieEntity
import com.android.themoviedb.presentation.component.LoadingNextPageItem
import com.android.themoviedb.presentation.component.PageLoader
import kotlinx.coroutines.launch

@Composable
fun MovieScreen(
    allMovies: LazyPagingItems<MovieEntity>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(snackbarHost =
        {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        LazyColumn {
            item { Spacer(modifier = modifier.padding(paddingValues)) }
            items(allMovies.itemCount) { index ->
                ItemMovie(
                    movieEntity = allMovies[index]!!,
                    navController = navController
                )
            }

            allMovies.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = allMovies.loadState.refresh as LoadState.Error
                        item {
                            LaunchedEffect(key1 = true) {
                                coroutineScope.launch {
                                    val snackBarResult = snackBarHostState.showSnackbar(
                                        message = error.error.localizedMessage!!,
                                        actionLabel = "Retry",
                                        duration = SnackbarDuration.Indefinite
                                    )

                                    when (snackBarResult) {
                                        SnackbarResult.ActionPerformed -> retry()
                                        SnackbarResult.Dismissed -> Log.i("SnackBarTag", "dismissed")
                                    }
                                }
                            }
                            /*ErrorMessage(
                                modifier = Modifier.fillParentMaxSize(),
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })*/
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = allMovies.loadState.append as LoadState.Error
                        item {
                            LaunchedEffect(key1 = true) {
                                coroutineScope.launch {
                                    val snackBarResult = snackBarHostState.showSnackbar(
                                        message = error.error.localizedMessage!!,
                                        actionLabel = "Retry",
                                        duration = SnackbarDuration.Indefinite
                                    )

                                    when (snackBarResult) {
                                        SnackbarResult.ActionPerformed -> retry()
                                        SnackbarResult.Dismissed -> Log.i("SnackBarTag", "dismissed")
                                    }
                                }
                            }
                            /*ErrorMessage(
                                modifier = Modifier,
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })*/
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.padding(4.dp)) }
        }
    }

}




