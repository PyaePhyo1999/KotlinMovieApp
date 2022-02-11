package com.example.mymovie.api.vo

import androidx.room.Entity

@Entity(tableName = "genre")
data class GenreVO(
    val id: Int,
    val name: String
)