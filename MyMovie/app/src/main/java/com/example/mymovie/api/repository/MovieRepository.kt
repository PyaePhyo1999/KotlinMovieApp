package com.example.mymovie.api.repository

import com.example.mymovie.api.RetrofitInstance
import com.example.mymovie.api.response.MovieGenreResponse
import com.example.mymovie.api.response.MovieVideoResponse
import com.example.mymovie.api.response.NowPlayingMovieResponse
import com.example.mymovie.api.response.PopularMovieResponse
import com.example.mymovie.api.services.MovieService
import com.example.mymovie.api.vo.DetailVO

class MovieRepository {


    private val instance by lazy {
         RetrofitInstance().getRetrofitInstance()
    }

    suspend fun getPopularMovie() : PopularMovieResponse{
        val service = instance?.create(MovieService::class.java)
        return service!!.getMoviePopular()

    }
    suspend fun getNowPlayingMovie() : NowPlayingMovieResponse{
        val service = instance?.create(MovieService::class.java)
        return service!!.getNowPlaying()
    }

//    suspend fun getMovieGenre() : MovieGenreResponse{
//        val service = instance?.create(MovieService::class.java)
//        return service!!.getGenre()
//    }

    suspend fun getMovieVideo(id : String) : MovieVideoResponse{
        val service = instance?.create(MovieService::class.java)
        return service!!.getVideo(id)
    }

    suspend fun getDetail(id : String) : DetailVO{
        val service = instance?.create(MovieService::class.java)
        return service!!.getDetail(id)
    }


}