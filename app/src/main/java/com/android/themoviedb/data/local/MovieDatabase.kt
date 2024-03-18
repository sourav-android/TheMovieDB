package com.android.themoviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.themoviedb.domain.model.movie.MovieEntity
import com.android.themoviedb.domain.model.movie.MovieRemoteKeys

@Database(
    entities = [MovieEntity::class, MovieRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDAO(): MovieDao
    abstract fun remoteKeysDAO(): MovieRemoteKeysDao
}