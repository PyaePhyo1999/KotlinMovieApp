package com.example.mymovie.api.vo

data class DetailVO(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<GenreVO>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<LanguageVO>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

//
//@ColumnInfo(name = "backdrop-path") val backdrop_path: String,
//@TypeConverters(DataConverter::class)
//@ColumnInfo(name = "genre_ids") val genre_ids: List<Int>,
//@PrimaryKey(autoGenerate = true) val id: Int,
//@ColumnInfo(name = "original-language") val original_language: String,
//@ColumnInfo(name = "original-title") val original_title: String,
//@ColumnInfo(name = "overview")val overview: String,
//@ColumnInfo(name = "popularity") val popularity: Double,
//@ColumnInfo(name = "poster-path")val poster_path: String?,
//@ColumnInfo(name = "release-date")val release_date: String,
//@ColumnInfo(name = "title") val title: String,
//@ColumnInfo(name = "video")val video: Boolean,
//@ColumnInfo(name = "vote-average")val vote_average: Double,
//@ColumnInfo(name = "vote-count") val vote_count: Int

