package com.ajs.quotes.api

import androidx.lifecycle.LiveData
import com.ajs.quotes.data.CategoryData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api.json")
   suspend fun getData(): Response<List<CategoryData>>

}