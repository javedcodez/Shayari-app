package com.ajs.quotes.data

class SortHelper {

     fun sortCategory(categoryData: List<CategoryData>):
            List<CategoryData> {
        return categoryData.distinctBy { it.category }.sortedBy { it.category }
    }
     fun sortAuthor(categoryData: List<CategoryData>):
            List<CategoryData> {
        return categoryData.distinctBy { it.author }.sortedBy { it.author }
    }

}