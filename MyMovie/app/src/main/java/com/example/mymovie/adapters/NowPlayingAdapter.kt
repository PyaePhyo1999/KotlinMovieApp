package com.example.mymovie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovie.AppUtils
import com.example.mymovie.delegates.MovieItemDelegate
import com.example.mymovie.R
import com.example.mymovie.databinding.ItemNowPlayingBinding
import com.example.mymovie.api.vo.NowPlayingVO

class NowPlayingAdapter(private var delegate: MovieItemDelegate, private val nowPlayingList: MutableList<NowPlayingVO>) : RecyclerView.Adapter<NowPlayingAdapter.TvSeriesViewHolder>() {

    inner class TvSeriesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
         private lateinit var binding: ItemNowPlayingBinding

         fun bindData(nowPlayingVO: NowPlayingVO){
             binding = ItemNowPlayingBinding.bind(itemView)

//             binding.TvNowPlaying.text = nowPlayingVO.title

                 Glide.with(itemView)
                     .load(AppUtils.IMG_BASE_URL+nowPlayingVO.poster_path)
                     .into(binding.ivNowPlaying)




             binding.cvNowPlaying.setOnClickListener {
                 delegate.onTapNowPlayingItem(nowPlayingVO)
             }
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvSeriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_now_playing,parent,false)
        return TvSeriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvSeriesViewHolder, position: Int) {
             holder.bindData(nowPlayingList[position])
    }

    override fun getItemCount(): Int {
        return nowPlayingList.size
    }
}