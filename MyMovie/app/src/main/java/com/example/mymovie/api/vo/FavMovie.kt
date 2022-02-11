package com.example.mymovie.api.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favMovie")
class FavMovie (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val movieId : String,
    val lan : String,
    val movieName : String,
    val rate : Double,
    val image : String

)