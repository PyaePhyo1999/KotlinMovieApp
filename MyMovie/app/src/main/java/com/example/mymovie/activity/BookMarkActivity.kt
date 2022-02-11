package com.example.mymovie.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovie.adapters.BookmarkAdapter
import com.example.mymovie.api.vo.FavMovie
import com.example.mymovie.api.vo.FavoriteMovie
import com.example.mymovie.databinding.ActivityBookMarkBinding
import com.example.mymovie.delegates.FavItemDelegate
import com.example.mymovie.local.DatabaseProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executors

class BookMarkActivity : BaseActivity(),FavItemDelegate {

    lateinit var binding: ActivityBookMarkBinding
    private lateinit var bmAdapter : BookmarkAdapter
    private val executor = Executors.newSingleThreadExecutor()!!

    private lateinit var favNoteReference: DatabaseReference

    companion object{
        fun intent(context: Context) : Intent {
            return Intent(context, BookMarkActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookMarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bmAdapter = BookmarkAdapter(this)

        binding.apply {

            rvBookmark.layoutManager = LinearLayoutManager(this@BookMarkActivity,LinearLayoutManager.VERTICAL,false)
            rvBookmark.adapter = bmAdapter
            executeDb()

            ivBackPress.setOnClickListener{
                toMainActivity()
                finishAffinity()
            }


        }

//        favNoteReference = Firebase.database.reference.child("favorite")

//        favNoteReference.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                    val favList = snapshot.children.map { data->
//                       val movieId = data.key!!
//                       val name = data.child("name").getValue<String>()!!
//                       val image = data.child("image").getValue<String>()!!
//                       val lan = data.child("lan").getValue<String>()!!
//                       val id = data.child("image").getValue<String>()!!
//                       val rating = data.child("rating").getValue<Double>()!!
//
//                       FavoriteMovie(
//                           id = id,
//                           movieId = movieId,
//                           movieName = name,
//                           lan = lan,
//                           image = image,
//                           rate = rating
//                       )
//
//                   }
//                   bmAdapter.submitList(favList)
//               }
//
//
//
//
//            override fun onCancelled(error: DatabaseError) {
//              error.toException().printStackTrace()
//            }
//
//        })

    }
    private fun executeDb() {
        executor.execute {
            val database = DatabaseProvider.instance(this)
            val dao = database.movieDao()
            val getData = dao.getMovie()
            runOnUiThread {
                bmAdapter.setItem(getData)
            }
        }
    }

    override fun onTapDelete(item: FavMovie) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete Item")
            .setMessage("Are you sure to delete Items")
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Yes") { dialog, _ ->
                executor.execute {
                    DatabaseProvider.instance(this).movieDao().deleteMovie(item)
                    executeDb()
                }

                dialog.dismiss()
            }.create()
        dialog.show()
    }



}