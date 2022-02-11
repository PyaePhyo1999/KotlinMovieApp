package com.example.mymovie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovie.AppUtils
import com.example.mymovie.R
import com.example.mymovie.api.vo.VideoVO
import com.example.mymovie.databinding.ItemTrailerBinding
import com.example.mymovie.delegates.TrailerDelegate
import java.util.regex.Matcher
import java.util.regex.Pattern

class MovieTrailerAdapter(private val trailerList : MutableList<VideoVO>, private var delegate : TrailerDelegate, private val image: String) : RecyclerView.Adapter<MovieTrailerAdapter.MovieCastViewHolder>() {

    inner class MovieCastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ItemTrailerBinding
         fun bindData(trailerVO : VideoVO){
             binding = ItemTrailerBinding.bind(itemView)
             binding.tvTrailer.text = trailerVO.name

//             val videoId = extractYoutubeId(AppUtils.BASE_YOUTUBE_URL+trailerVO.key)
//             val img_url = "http://img.youtube.com/vi/$videoId/0.jpg"

             Glide.with(itemView)
                 .load(AppUtils.IMG_BASE_URL+image)
                 .into(binding.ivTrailer)

             binding.ivTrailer.setOnClickListener{
                 delegate.onTapTrailer(trailerVO)
             }

         }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trailer, parent, false)
        return MovieCastViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        holder.bindData(trailerList[position])
    }

    override fun getItemCount(): Int {
        return trailerList.size
    }

}