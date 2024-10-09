package com.ajs.quotes

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajs.quotes.adapter.AuthorAdapter
import com.ajs.quotes.adapter.CategoriesAdapter
import com.ajs.quotes.adapter.TrendingAdapter
import com.ajs.quotes.api.RetrofitObject
import com.ajs.quotes.data.CategoryData
import com.ajs.quotes.data.SortHelper
import com.ajs.quotes.databinding.ActivityMainBinding
import com.ajs.quotes.repo.MainRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var errorDilog: Dialog

    private lateinit var viewModel : MainViewModel

    private lateinit var authorAadapter: AuthorAdapter
    private lateinit var categoryAadapter: CategoriesAdapter
    private lateinit var trendingAadapter: TrendingAdapter

    private lateinit var loading : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        errorDilog = Dialog(this)
        loading = Dialog(this)

        loading.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        errorDilog.window?.setBackgroundDrawable(getDrawable(R.drawable.bg_item))

        showLoading(true)
        setAuthor()
        setCategory()
        setTrending()

        val apiService = RetrofitObject.apiSrvice
        val repo = MainRepo(apiService)
        viewModel = ViewModelProvider(
            this,
            MainViewModel.MainViewModelFactory(repo)
        )[MainViewModel::class.java]

        binding.swipeRefresh.setOnRefreshListener {
            refreshData()
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.data.observe(this) {
            showLoading(false)
            if (it != null) {

                trendingAadapter.submitList(it)

                authorAadapter = AuthorAdapter(applicationContext,it)
                binding.authorRecycle.adapter = authorAadapter

                val aauthor = SortHelper().sortAuthor(it)
                authorAadapter.submitList(aauthor)

                categoryAadapter = CategoriesAdapter(applicationContext,it)
                binding.categoriesRecycle.adapter = categoryAadapter
                val category = SortHelper().sortCategory(it)
                categoryAadapter.submitList(category)

            } else {
                shoeError("Somthing Error in Our App")
                Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imgMenu.setOnClickListener {
            showAbout()
        }
    }

    fun refreshData(){
        CoroutineScope(Dispatchers.IO).launch{
            viewModel.refreshData()
        }
    }

    private fun showLoading(isShow:Boolean){
        if(isShow) {
            loading.setContentView(R.layout.layout_loading)
            loading.setCancelable(false)
            loading.show()
        }else{
            loading.hide()
        }
    }

    private fun showAbout(){
        val dig = Dialog(this)
        dig.setContentView(R.layout.layout_error_dialog)
        dig.setCancelable(true)
        dig.findViewById<TextView>(R.id.text).text = "Welcome to Quotes, the ultimate destination for inspiration, motivation, and wisdom!  "
        dig.findViewById<Button>(R.id.rightBtn).setText("Ok")
        dig.findViewById<Button>(R.id.rightBtn).setOnClickListener {
            dig.hide()
        }

        dig.findViewById<Button>(R.id.leftBtn).visibility = View.GONE

        dig.show()
    }

    private fun shoeError(msg: String) {
        errorDilog.setContentView(R.layout.layout_error_dialog)
        errorDilog.setCancelable(false)
        errorDilog.findViewById<TextView>(R.id.text).text = msg
        errorDilog.findViewById<Button>(R.id.rightBtn).setOnClickListener {
            errorDilog.hide()
            refreshData()
        }
        errorDilog.findViewById<Button>(R.id.leftBtn).setOnClickListener {
            finish()
        }

        errorDilog.show()

    }

    private fun setAuthor() {
        binding.authorRecycle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setCategory() {
        binding.categoriesRecycle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setTrending() {
        binding.trendingRecycle.layoutManager = LinearLayoutManager(this)
        trendingAadapter = TrendingAdapter(applicationContext)
        binding.trendingRecycle.adapter = trendingAadapter
    }

}