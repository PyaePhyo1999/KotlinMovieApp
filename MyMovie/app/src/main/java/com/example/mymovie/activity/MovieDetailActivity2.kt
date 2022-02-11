package com.example.mymovie.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mymovie.AppUtils
import com.example.mymovie.adapters.GenreAdapter
import com.example.mymovie.adapters.MovieTrailerAdapter
import com.example.mymovie.api.response.MovieVideoResponse
import com.example.mymovie.api.vo.*
import com.example.mymovie.databinding.ActivityMovieDetailBinding
import com.example.mymovie.delegates.TrailerDelegate
import com.example.mymovie.viewModel.MovieViewModel

class MovieDetailActivity2 : BaseActivity(), TrailerDelegate {

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, MovieDetailActivity2::class.java)
        }
    }

    private val mViewModel: MovieViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var trailerAdapter: MovieTrailerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//Retrieve dataObj from Intent
        val getPopularObj = intent.getSerializableExtra("popularMovie") as PopularMovieVO?
        val getNowPlayingObj = intent.getSerializableExtra("nowPlaying") as NowPlayingVO?


        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel.getMovie()

        if (getPopularObj != null) {
          getPopularId(getPopularObj.id.toString())
        }

        if (getNowPlayingObj != null) {
            getNowPlayingId(getNowPlayingObj.id.toString())
        }

//Trailer View Model observe
        mViewModel.trailer.observe(this){
            if (getNowPlayingObj!=null){
                trailer(it,getNowPlayingObj.backdrop_path)
            }
            if(getPopularObj!=null){
                 trailer(it,getPopularObj.backdrop_path)
            }
        }

//Movie Detail View Model observe
        mViewModel.detail.observe(this) {
            binding.apply {
                if (getPopularObj != null) {
                     bindData(it)
                }
                if (getNowPlayingObj != null) {
                    bindData(it)
                }

            }
        }

        binding.apply {
            ivClose.setOnClickListener {
                toMainActivity()
            }
//Save Fav Movie
            ivBookmark.setOnClickListener {
                Toast.makeText(applicationContext,"fav my movie",Toast.LENGTH_SHORT).show()
                if (getPopularObj!=null ){
                    val favMovie = FavMovie(0,
                        getPopularObj.id.toString(),
                        getPopularObj.original_language,
                        getPopularObj.original_title,
                        getPopularObj.vote_average,
                        getPopularObj.backdrop_path

                    )
                    mViewModel.saveMovie(favMovie,applicationContext)
                }
                if (getNowPlayingObj!=null){
                    val favMovie = FavMovie(0,
                        getNowPlayingObj.id.toString(),
                        getNowPlayingObj.original_language,
                        getNowPlayingObj.original_title,
                        getNowPlayingObj.vote_average,
                        getNowPlayingObj.backdrop_path

                    )
                    mViewModel.saveMovie(favMovie,applicationContext)
                }
            }
        }
    }

//Retrieve popular Id
    private fun getPopularId(id:String){
        mViewModel.getDetail(id)
        mViewModel.getMovieVideo(id)
    }

//Retrieve NowPlaying Id
    private fun getNowPlayingId(id:String){
        mViewModel.getDetail(id)
        mViewModel.getMovieVideo(id)
    }

//Detail Data Binding
    private fun bindData(it: DetailVO){
        binding.apply {
            tvDetailMovieName.text = it.title

            Glide.with(applicationContext)
                .load("https://image.tmdb.org/t/p/original" + it.backdrop_path)
                .into(binding.ivDetailPoster)

            tvMovieOverView.text = it.overview
            tvRating.text = it.vote_average.toString()
            tvReleaseDate.text = "Release Date - " + it.release_date
            tvRunTime.text = "Run time - " + (it.runtime/60).toString()+ ":" +(it.runtime%60).toString()+" mins"

            if (it.adult) {
                tvMovieRate.visibility = View.VISIBLE
            }

            genreAdapter = GenreAdapter(it.genres as MutableList<GenreVO>)
            rvGenre.layoutManager = LinearLayoutManager(
                this@MovieDetailActivity2, LinearLayoutManager.HORIZONTAL, false
            )
            binding.rvGenre.adapter = genreAdapter
        }

    }

//Trailer Adapter
    private fun trailer(it : MovieVideoResponse,img: String){
        trailerAdapter = MovieTrailerAdapter(
            it.results as MutableList<VideoVO>,
            this,
             img
        )
        binding.rvTrailer.layoutManager = LinearLayoutManager(
            this@MovieDetailActivity2,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvTrailer.adapter = trailerAdapter
    }

    override fun onTapTrailer(trailerVo: VideoVO) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppUtils.BASE_YOUTUBE_URL + trailerVo.key)
            )
        )

    }

}
