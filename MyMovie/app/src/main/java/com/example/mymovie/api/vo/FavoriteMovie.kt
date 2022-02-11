package com.example.mymovie.api.vo

import androidx.room.PrimaryKey

data class FavoriteMovie(
    val id: String,
    val movieId : String,
    val lan : String,
    val movieName : String,
    val rate : Double,
    val image : String
)
