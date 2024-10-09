package com.ajs.quotes.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    private val URL = "https://shayri-app-api.netlify.app/"
    private val retrofit: Retrofit by lazy {
        synchronized(this){
            Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    val apiSrvice = retrofit.create(ApiService::class.java)

}