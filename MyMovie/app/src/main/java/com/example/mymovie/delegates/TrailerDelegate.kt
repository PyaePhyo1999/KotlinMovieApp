package com.example.mymovie.delegates

import com.example.mymovie.api.vo.VideoVO

interface TrailerDelegate {
    fun onTapTrailer(trailerVo : VideoVO)
}