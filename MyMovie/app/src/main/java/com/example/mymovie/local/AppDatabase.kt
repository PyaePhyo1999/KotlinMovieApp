package com.example.mymovie.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mymovie.api.vo.DetailVO
import com.example.mymovie.api.vo.FavMovie
import com.example.mymovie.api.vo.PopularMovieVO
import com.example.mymovie.local.dao.MovieDao

@Database(entities = [FavMovie::class],version = 1,exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AppDatabase: RoomDatabase() {
     abstract fun movieDao(): MovieDao
}