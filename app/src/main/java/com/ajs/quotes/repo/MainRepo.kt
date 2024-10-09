package com.ajs.quotes.repo

import androidx.lifecycle.MutableLiveData
import com.ajs.quotes.api.ApiService
import com.ajs.quotes.data.CategoryData

class MainRepo (private val apiService: ApiService) {

    val dataLive = MutableLiveData<List<CategoryData>?>()

    suspend fun getData(){
        try {
            val data = apiService.getData()
            if(data.isSuccessful){
                val t = data.body()
                dataLive.postValue(t)
            }else{
                dataLive.postValue(null)
            }
        }catch (e:Exception){
            dataLive.postValue(null)
        }

    }

}