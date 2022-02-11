package com.example.mymovie.api

import com.example.mymovie.AppUtils.Companion.API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    private var retrofit : Retrofit? = null

    //    private val API_URL = "http://13.233.138.230:80/"


    fun getRetrofitInstance() : Retrofit?
    {
        if (retrofit == null)
        {
            retrofit = Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build()
            //retrofit = Retrofit.Builder().baseUrl(API_URL).addCallAdapterFactory(NetworkResponseAdapterFactory()).build()
        }
        return retrofit
    }

}