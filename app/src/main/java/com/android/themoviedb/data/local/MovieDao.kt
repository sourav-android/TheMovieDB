package com.android.themoviedb.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.themoviedb.core.utils.Constants.MOVIE_TABLE
import com.android.themoviedb.domain.model.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movieEntities: List<MovieEntity>)

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun getAllMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE movieId = :movieId")
    fun getMovie(movieId: Int): Flow<MovieEntity>

    @Query("DELETE FROM $MOVIE_TABLE")
    suspend fun deleteAllMovies()

}