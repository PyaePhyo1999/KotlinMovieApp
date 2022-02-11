package com.example.mymovie.api.services

import com.example.mymovie.api.response.MovieGenreResponse
import com.example.mymovie.api.response.MovieVideoResponse
import com.example.mymovie.api.response.NowPlayingMovieResponse
import com.example.mymovie.api.response.PopularMovieResponse
import com.example.mymovie.api.vo.DetailVO
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("movie/popular?api_key=e04f0b223fbc6cafa71cb97b491b141b")
    suspend fun getMoviePopular(): PopularMovieResponse

    @GET("movie/now_playing?api_key=e04f0b223fbc6cafa71cb97b491b141b")
    suspend fun getNowPlaying() : NowPlayingMovieResponse

//    @GET("genre/movie/list?api_key=e04f0b223fbc6cafa71cb97b491b141b")
//    suspend fun getGenre() : MovieGenreResponse

    @GET("movie/{movie_id}/videos?api_key=e04f0b223fbc6cafa71cb97b491b141b")
    suspend fun getVideo(@Path("movie_id") movieId: String) : MovieVideoResponse

    @GET("movie/{movie_id}?api_key=e04f0b223fbc6cafa71cb97b491b141b")
    suspend fun getDetail(@Path("movie_id") movieId: String) : DetailVO
}

//https://api.themoviedb.org/3/movie/popular?api_key=e04f0b223fbc6cafa71cb97b491b141b
//https://api.themoviedb.org/3/movie/{movie_id}?api_key=e04f0b223fbc6cafa71cb97b491b141b
//https://api.themoviedb.org/3/movie/157336/videos?api_key=e04f0b223fbc6cafa71cb97b491b141b
//https://api.themoviedb.org/3/movie/157336?api_key=e04f0b223fbc6cafa71cb97b491b141b