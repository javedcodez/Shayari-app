package com.ajs.quotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajs.quotes.adapter.TrendingAdapter
import com.ajs.quotes.api.RetrofitObject
import com.ajs.quotes.data.CategoryData
import com.ajs.quotes.data.SortHelper
import com.ajs.quotes.databinding.ActivityShayriListBinding
import com.ajs.quotes.repo.MainRepo

class ShayriListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityShayriListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShayriListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trendingRecycle.layoutManager = LinearLayoutManager(this )
        val trendingAadapter = TrendingAdapter(applicationContext)
        binding.trendingRecycle.adapter = trendingAadapter


        val listData = intent.getSerializableExtra("data") as List<CategoryData>

        binding.textview.text = intent.getStringExtra("key")

            val isAuthor = intent.getBooleanExtra("isAuthor",false)
            if(isAuthor) {
                val data = sortAuthor(listData, intent.getStringExtra("key")!!)
                trendingAadapter.submitList(data)
            }else{
                val data = sort(listData, intent.getStringExtra("key")!!)
                trendingAadapter.submitList(data)
            }


    }

    fun sort(it: List<CategoryData>,category:String):MutableList<CategoryData>{
        val data : MutableList<CategoryData> = mutableListOf()
        for(i in it){
            if(i.category == category){
                data.add(i)
            }
        }
        return data
    }

    fun sortAuthor(it: List<CategoryData>,author:String):MutableList<CategoryData>{
        val data : MutableList<CategoryData> = mutableListOf()
        for(i in it){
            if(i.author == author){
                data.add(i)
            }
        }
        return data
    }
}