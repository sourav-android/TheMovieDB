package com.android.themoviedb.presentation.screens.movie

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.android.themoviedb.domain.model.movie.MovieEntity
import com.android.themoviedb.presentation.resource.ErrorMessage
import com.android.themoviedb.presentation.resource.LoadingNextPageItem
import com.android.themoviedb.presentation.resource.PageLoader

@Composable
fun MovieScreen(
    allMovies: LazyPagingItems<MovieEntity>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    LazyColumn {
        item { Spacer(modifier = modifier.padding(4.dp)) }
        items(allMovies.itemCount){ index ->
            ItemMovie(movieEntity = allMovies[index]!!,
                navController = navController)
        }

        allMovies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = allMovies.loadState.refresh as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier.fillParentMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingNextPageItem(modifier = Modifier) }
                }

                loadState.append is LoadState.Error -> {
                    val error = allMovies.loadState.append as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier,
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.padding(4.dp)) }
    }

}




