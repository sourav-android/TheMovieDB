package com.android.themoviedb.presentation.screens.movie

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.android.themoviedb.BuildConfig
import com.android.themoviedb.domain.model.movie.MovieEntity

@Composable
fun ItemMovie(
    modifier: Modifier = Modifier,
    movieEntity: MovieEntity,
    navController: NavHostController,

    ) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {/* onClick()*/ },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        val painter = rememberAsyncImagePainter(BuildConfig.MOVIE_POSTER_URL + movieEntity.posterPath)
        val transition by animateFloatAsState(
            targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f, label = ""
        )
        Column {
            Box(
                modifier = modifier
                    .height(150.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .alpha(transition)
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.TopEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow
                    )

                    Text(
                        text =movieEntity.rating.orEmpty(),
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Yellow,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }

            Text(
                    text = movieEntity.title ?: "",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                )

            Spacer(modifier = modifier.padding(vertical = 4.dp))

            Text(
                    text = movieEntity.overview?: "",
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
        }
    }
}