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

    @Upsert
    suspend fun upsertAll(movieEntity: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    /*@Query("SELECT * FROM $MOVIE_TABLE WHERE movie_id=:id")
    suspend fun getById(id: Int): MovieResponse<MovieDto>?*/

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM $MOVIE_TABLE")
    suspend fun clearAll()

}