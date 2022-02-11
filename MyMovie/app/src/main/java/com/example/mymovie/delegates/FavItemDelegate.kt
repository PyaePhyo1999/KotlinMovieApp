package com.example.mymovie.delegates

import com.example.mymovie.api.vo.FavMovie
import com.example.mymovie.api.vo.FavoriteMovie

interface FavItemDelegate {
    fun onTapDelete(fav : FavMovie)
}