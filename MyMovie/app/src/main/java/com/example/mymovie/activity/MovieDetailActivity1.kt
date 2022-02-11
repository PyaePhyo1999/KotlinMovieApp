package com.example.mymovie.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mymovie.AppUtils
import com.example.mymovie.adapters.GenreAdapter
import com.example.mymovie.adapters.MovieCastAdapter
import com.example.mymovie.adapters.MovieTrailerAdapter
import com.example.mymovie.api.vo.*
import com.example.mymovie.databinding.ActivityMovieDetailBinding
import com.example.mymovie.delegates.TrailerDelegate
import com.example.mymovie.viewModel.MovieViewModel

class MovieDetailActivity1 : AppCompatActivity(),TrailerDelegate {


    companion object{
        fun intent(context: Context): Intent {
            return Intent(context, MovieDetailActivity1::class.java)
        }
    }

    private val mViewModel: MovieViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var castAdapter: MovieCastAdapter
    private lateinit var trailerAdapter: MovieTrailerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val getPopularObj = intent.getSerializableExtra("popularMovie") as PopularMovieVO?


        val getNowPlayingObj = intent.getSerializableExtra("nowPlaying") as NowPlayingVO?


        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mViewModel.getMovie()
        if (getPopularObj!=null){
            mViewModel.getMovieVideo(getPopularObj.id.toString())
        }
        if (getNowPlayingObj!=null)
        {
            mViewModel.getMovieVideo(getNowPlayingObj.id.toString())
        }

        mViewModel.trailer.observe(this){

            if (getPopularObj!=null){
                trailerAdapter = MovieTrailerAdapter(it.results as MutableList<VideoVO>,this,getPopularObj.backdrop_path)
                binding.rvTrailer.layoutManager = LinearLayoutManager(this@MovieDetailActivity1,LinearLayoutManager.HORIZONTAL,false)
                binding.rvTrailer.adapter = trailerAdapter
            }
            if (getNowPlayingObj!=null)
            {
                trailerAdapter = MovieTrailerAdapter(it.results as MutableList<VideoVO>,this,getNowPlayingObj.backdrop_path)
                binding.rvTrailer.layoutManager = LinearLayoutManager(this@MovieDetailActivity1,LinearLayoutManager.HORIZONTAL,false)
                binding.rvTrailer.adapter = trailerAdapter
            }


        }

//        mViewModel.movieGenre.observe(this){
//
//            if (getPopularObj!=null){
//                val firstListIds = getPopularObj.genre_ids.map {it}
//                val result = it.genres.filter { it.id in firstListIds }
//                genreAdapter = GenreAdapter( result as MutableList<GenreVO>)
//
//            }
//            if (getNowPlayingObj!=null){
//                val firstListIds = getNowPlayingObj.genre_ids.map {it}
//                val result = it.genres.filter { it.id in firstListIds }
//                genreAdapter = GenreAdapter( result as MutableList<GenreVO>)
//            }
//
//            //Genre Adapter
//            binding.rvGenre.layoutManager = LinearLayoutManager(this@MovieDetailActivity1
//                ,LinearLayoutManager.HORIZONTAL,false)
//            binding.rvGenre.adapter = genreAdapter
//        }

        castAdapter = MovieCastAdapter()

        binding.apply {

            if(getPopularObj!=null) {
                tvDetailMovieName.text = getPopularObj.title

                Glide.with(applicationContext)
                    .load("https://image.tmdb.org/t/p/original" + getPopularObj.backdrop_path)
                    .into(binding.ivDetailPoster)

                tvMovieOverView.text = getPopularObj.overview
                tvRating.text = getPopularObj.vote_average.toString()
                tvReleaseDate.text = "Release Date - "+getPopularObj.release_date

                if (getPopularObj.adult) {
                    tvMovieRate.visibility = View.VISIBLE
                }
            }

            if(getNowPlayingObj!=null){
                tvDetailMovieName.text = getNowPlayingObj.title

                Glide.with(applicationContext)
                    .load("https://image.tmdb.org/t/p/original" + getNowPlayingObj.backdrop_path)
                    .into(binding.ivDetailPoster)

                tvMovieOverView.text = getNowPlayingObj.overview
                tvRating.text = getNowPlayingObj.vote_average.toString()
                tvReleaseDate.text = "Release Date-"+getNowPlayingObj.release_date

                if (getNowPlayingObj.adult) {
                    tvMovieRate.visibility = View.VISIBLE
                }
            }
//            rvCast.layoutManager = LinearLayoutManager(this@MovieDetailActivity
//                ,LinearLayoutManager.HORIZONTAL,false)
//            rvCast.adapter = castAdapter

            ivClose.setOnClickListener{
                toMainActivity()
            }

            ivBookmark.setOnClickListener {

                Toast.makeText(applicationContext,"fav my movie",Toast.LENGTH_SHORT).show()
//                if (getPopularObj!=null){
//                    val favMovie = FavMovie(0,getPopularObj.id.toString())
//                    mViewModel.saveMovie(favMovie,applicationContext)
//                }
            }


        }

    }
//    private fun openWebPage(url: String){
//          val webpage:Uri = Uri.parse(url)
//          val intent = Intent(Intent.ACTION_VIEW,webpage)
//          startActivity(intent)
//    }

    private fun toMainActivity(){
        intent = MainActivity.intent(applicationContext)
        startActivity(intent)
    }

    override fun onTapTrailer(trailerVo: VideoVO) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AppUtils.BASE_YOUTUBE_URL + trailerVo.key)))

    }

}
