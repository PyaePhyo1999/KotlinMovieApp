package com.example.mymovie.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
     private var database: AppDatabase?=null

     fun instance(context: Context) : AppDatabase{
            if (database==null){
                database = Room.databaseBuilder(context,AppDatabase::class.java,"fav.db").build()

            }
            return database!!

        }


}