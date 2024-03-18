package com.android.themoviedb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.themoviedb.core.utils.Constants
import com.android.themoviedb.domain.model.movie.MovieRemoteKeys

@Dao
interface MovieRemoteKeysDao {

    @Query("SELECT * FROM ${Constants.MOVIE_REMOTE_KEYS} WHERE id = :movieId")
    suspend fun getMovieRemoteKeys(movieId: Int): MovieRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllMovieRemoteKeys(movieRemoteKeys : List<MovieRemoteKeys>)

    @Query("DELETE FROM movie_remote_keys")
    suspend fun deleteAllMovieRemoteKeys()

}