package com.example.mymovie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovie.AppUtils
import com.example.mymovie.delegates.MovieItemDelegate
import com.example.mymovie.R
import com.example.mymovie.databinding.ItemPopularMovieBinding
import com.example.mymovie.api.vo.PopularMovieVO

class PopularMovieAdapter(private var delegate: MovieItemDelegate, private var popularMovieList : MutableList<PopularMovieVO>) : RecyclerView.Adapter<PopularMovieAdapter.NewMovieViewHolder>() {


    inner class NewMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         private lateinit var binding : ItemPopularMovieBinding

         fun bindData(popularMovieVO : PopularMovieVO){
             binding = ItemPopularMovieBinding.bind(itemView)


             Glide.with(itemView.context)
                 .load(AppUtils.IMG_BASE_URL+popularMovieVO.poster_path)
                 .into(binding.ivNewMovie)

//             if(result.poster_path != null){
//
//             }

//             Picasso.get()
//                 .load("https://image.tmdb.org/t/p/original/"+result.backdrop_path)
//             .into(binding.ivNewMovie)


             binding.cvNewMovie.setOnClickListener {
                 delegate.onTapPopularItem(popularMovieVO)

             }

         }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movie,parent,false)
        return NewMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewMovieViewHolder, position: Int) {
          holder.bindData( popularMovieList[position])
    }

    override fun getItemCount(): Int {
       return popularMovieList.size
    }

}
