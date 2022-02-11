package com.example.mymovie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovie.R
import com.example.mymovie.api.vo.GenreVO
import com.example.mymovie.databinding.ItemGenreBinding

class GenreAdapter(private val genre : MutableList<GenreVO>) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         private lateinit var binding : ItemGenreBinding

         fun bindData(genre : GenreVO){
             binding = ItemGenreBinding.bind(itemView)

             binding.tvGenre.text = genre.name
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre,parent,false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
         holder.bindData(genre[position])
    }

    override fun getItemCount(): Int {
       return genre.size
    }

}
