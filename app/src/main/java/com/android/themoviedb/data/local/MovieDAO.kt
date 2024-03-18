package com.android.themoviedb.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.android.themoviedb.core.utils.Constants.MOVIE_TABLE

@Dao
interface MovieDAO {

    /*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    */
   /* @Upsert
    suspend fun upsertAll(movieEntity: List<MovieEntity>)

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM $MOVIE_TABLE")
    suspend fun clearAll()*/

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun getMovies(): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(quotes: List<MovieEntity>)

    @Query("DELETE FROM $MOVIE_TABLE")
    suspend fun deleteMovies()

}