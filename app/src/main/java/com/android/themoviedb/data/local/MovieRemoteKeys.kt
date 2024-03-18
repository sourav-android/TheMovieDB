package com.android.themoviedb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieRemoteKeys (
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val prevPage : Int?,
    val nextPage : Int?
)