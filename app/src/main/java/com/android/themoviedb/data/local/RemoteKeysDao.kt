package com.android.themoviedb.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM MovieRemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id : Int) : MovieRemoteKeys

    @Upsert
    suspend fun addAllRemoteKeys(remoteKeys: List<MovieRemoteKeys>)

    @Query("DELETE FROM MovieRemoteKeys")
    suspend fun deleteAllRemoteKeys()

}