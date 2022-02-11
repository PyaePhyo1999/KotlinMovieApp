package com.example.mymovie.delegates

import com.example.mymovie.api.vo.NowPlayingVO
import com.example.mymovie.api.vo.PopularMovieVO

interface MovieItemDelegate {
    fun onTapPopularItem(popularMovieVO: PopularMovieVO)
    fun onTapNowPlayingItem(nowPlayingVO: NowPlayingVO)
}