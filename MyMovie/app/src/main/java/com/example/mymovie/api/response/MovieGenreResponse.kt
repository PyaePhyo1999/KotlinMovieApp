package com.example.mymovie.api.response

import com.example.mymovie.api.vo.GenreVO

data class MovieGenreResponse(
    val genres: List<GenreVO>
)