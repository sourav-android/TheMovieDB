package com.android.themoviedb.domain.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.themoviedb.core.utils.Constants.MOVIE_REMOTE_KEYS

@Entity(tableName = MOVIE_REMOTE_KEYS)
data class MovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)