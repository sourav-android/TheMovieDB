package com.android.themoviedb.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.themoviedb.core.utils.Constants.MOVIE_TABLE
import java.io.Serializable

@Entity(tableName = MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "movie_id") val id: Int,
    @ColumnInfo(name = "movie_overview") val overview: String,
    @ColumnInfo(name = "movie_poster_path") val posterPath: String,
    @ColumnInfo(name = "movie_release_date") val releaseDate: String,
    @ColumnInfo(name = "movie_title") val title: String,
    @ColumnInfo(name = "voter_average") val voteAverage: Double,
) : Serializable


