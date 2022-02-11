package com.example.mymovie.api.response

import com.example.mymovie.api.vo.Dates
import com.example.mymovie.api.vo.NowPlayingVO
import com.google.gson.annotations.SerializedName

data class NowPlayingMovieResponse(
    val dates: Dates,
    val page: Int,
    @SerializedName("results")
    val nowPlayingVO: List<NowPlayingVO>,
    val total_pages: Int,
    val total_results: Int
)