package com.example.mymovie.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mymovie.api.vo.DetailVO
import com.example.mymovie.api.vo.FavMovie
import com.example.mymovie.api.vo.PopularMovieVO

@Dao
interface MovieDao {
@Query("Select * from favMovie")
fun getMovie() : List<FavMovie>

@Insert
fun insertMovie(favMovie: FavMovie)

@Delete
fun deleteMovie(favMovie: FavMovie)

}