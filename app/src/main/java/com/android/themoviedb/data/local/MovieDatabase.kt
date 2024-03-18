package com.android.themoviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class, MovieRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDAO(): MovieDAO
    abstract fun remoteKeysDAO(): RemoteKeysDao
}