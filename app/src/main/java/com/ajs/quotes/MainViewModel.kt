package com.ajs.quotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ajs.quotes.data.CategoryData
import com.ajs.quotes.repo.MainRepo
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepo) : ViewModel() {

    val data: LiveData<List<CategoryData>?> get() = repo.dataLive

    init {
        viewModelScope.launch {
            repo.getData()
        }
    }

    suspend fun refreshData() {
        repo.getData()
    }


    class MainViewModelFactory(private val repo: MainRepo) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repo) as T
        }
    }
}
