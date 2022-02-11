package com.example.mymovie.api.response

import com.example.mymovie.api.vo.PopularMovieVO
import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    val page: Int,
    @SerializedName("results")
    val popularMovieVO: List<PopularMovieVO>,
    val total_pages: Int,
    val total_results: Int
)