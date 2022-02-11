package com.example.mymovie.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovie.api.repository.MovieRepository
import com.example.mymovie.api.response.MovieVideoResponse
import com.example.mymovie.api.response.NowPlayingMovieResponse
import com.example.mymovie.api.response.PopularMovieResponse
import com.example.mymovie.api.vo.DetailVO
import com.example.mymovie.api.vo.FavMovie
import com.example.mymovie.local.DatabaseProvider
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class MovieViewModel : ViewModel() {

    private var movieRepository: MovieRepository = MovieRepository()

    private val _popularVM = MutableLiveData<PopularMovieResponse>()
    val popularVM: MutableLiveData<PopularMovieResponse> get() = _popularVM

    private val _nowPlayingVM = MutableLiveData<NowPlayingMovieResponse>()
    val nowPlayingVM: MutableLiveData<NowPlayingMovieResponse> get() = _nowPlayingVM

//    private val _movieGenre = MutableLiveData<MovieGenreResponse>()
//    val movieGenre: MutableLiveData<MovieGenreResponse> get() = _movieGenre

    private val _trailer = MutableLiveData<MovieVideoResponse>()
    val trailer: MutableLiveData<MovieVideoResponse> get() = _trailer


    private val _detail = MutableLiveData<DetailVO>()
    val detail: MutableLiveData<DetailVO> get() = _detail

    private val executor = Executors.newSingleThreadExecutor()

    fun getDetail(id: String) {
        viewModelScope.launch {
            try {
                _detail.value = movieRepository.getDetail(id)
            } catch (e: java.lang.Exception) {
            }

        }

    }

    fun getMovieVideo(id: String) {
        viewModelScope.launch {
            try {
                _trailer.value = movieRepository.getMovieVideo(id)

            } catch (e: java.lang.Exception) {

            }
        }
    }

    fun getMovie() {
        viewModelScope.launch {
            try {
                _popularVM.value = movieRepository.getPopularMovie()
                _nowPlayingVM.value = movieRepository.getNowPlayingMovie()
//                _movieGenre.value = movieRepository.getMovieGenre()

            } catch (e: Exception) {
            }
        }

    }

    fun saveMovie(favMovie: FavMovie, context: Context) {
        val saveMovie = FavMovie(
            id = 0,
            movieId = favMovie.movieId,
            lan = favMovie.lan,
            movieName = favMovie.movieName,
            rate = favMovie.rate,
            image = favMovie.image
        )
        executor.execute {
            DatabaseProvider.instance(context).movieDao().insertMovie(saveMovie)
        }
    }


}