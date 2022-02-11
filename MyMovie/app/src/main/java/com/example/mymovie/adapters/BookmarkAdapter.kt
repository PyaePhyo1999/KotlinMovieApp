package com.example.mymovie.adapters

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovie.AppUtils
import com.example.mymovie.R
import com.example.mymovie.api.vo.DetailVO
import com.example.mymovie.api.vo.FavMovie
import com.example.mymovie.api.vo.FavoriteMovie
import com.example.mymovie.api.vo.PopularMovieVO
import com.example.mymovie.databinding.ActivityBookMarkBinding
import com.example.mymovie.databinding.ItemBookmarkBinding
import com.example.mymovie.delegates.FavItemDelegate

class BookmarkAdapter(private val delegate: FavItemDelegate) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {


    private var favMovieList = listOf<FavMovie>()

    class BookmarkViewHolder(private var binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(favMovie: FavMovie) {
            binding = ItemBookmarkBinding.bind(itemView)
            binding.apply {
                tvLanguage.text = favMovie.lan
                tvMovieName.text = favMovie.movieName
                tvRating.text = favMovie.rate.toString()

                Glide.with(itemView)
                    .load(AppUtils.IMG_BASE_URL + favMovie.image)
                    .into(ivMovie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBookmarkBinding.inflate(layoutInflater, parent, false)
        val holder = BookmarkViewHolder(
            binding
        )

        binding.ivDelete.setOnClickListener() {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val itemDel = favMovieList[position]
                delegate.onTapDelete(itemDel)
            }

        }
        return holder
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bindData(favMovieList[position])
    }


    fun setItem(movie: List<FavMovie>) {
        favMovieList = movie
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return favMovieList.size
    }

}
