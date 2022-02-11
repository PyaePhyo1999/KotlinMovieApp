package com.example.mymovie.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type


class DataConverter : Serializable {
    @TypeConverter // note this annotation
    fun fromGenreString(genreId: List<Int>?): String? {
        if (genreId == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Int>>() {}.type
        return gson.toJson(genreId, type)
    }

    @TypeConverter // note this annotation
    fun toGenreIdList(genreId: String): List<Int>? {
        val gson = Gson()
        val type: Type = object :
            TypeToken<List<Int?>?>() {}.type

        return gson.fromJson(genreId, type)

    }
    }
