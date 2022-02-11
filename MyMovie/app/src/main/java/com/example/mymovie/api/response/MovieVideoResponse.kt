package com.example.mymovie.api.response

import com.example.mymovie.api.vo.VideoVO

data class MovieVideoResponse(
    val id: Int,
    val results: List<VideoVO>
)