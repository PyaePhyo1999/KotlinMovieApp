package com.example.mymovie.api.vo

import androidx.room.*
import com.example.mymovie.local.DataConverter
import java.io.Serializable

@Entity(tableName = "popularMovie")
data class PopularMovieVO(
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "backdrop-path") val backdrop_path: String,
    @TypeConverters(DataConverter::class)
    @ColumnInfo(name = "genre_ids") val genre_ids: List<Int>,
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "original-language") val original_language: String,
    @ColumnInfo(name = "original-title") val original_title: String,
    @ColumnInfo(name = "overview")val overview: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster-path")val poster_path: String?,
    @ColumnInfo(name = "release-date")val release_date: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "video")val video: Boolean,
    @ColumnInfo(name = "vote-average")val vote_average: Double,
    @ColumnInfo(name = "vote-count") val vote_count: Int
) : Serializable