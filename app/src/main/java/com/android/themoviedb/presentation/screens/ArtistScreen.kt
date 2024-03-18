package com.android.themoviedb.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ArtistScreen(
    modifier : Modifier = Modifier
) {
    Box(modifier = modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text = "Artist Screen",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyMedium,
        )
    }

}