package com.ajs.quotes.data

import java.io.Serializable

data class CategoryData(
    val id: Long,
    val shayri: String,
    val author: String,
    val category: String,
    val img : String
) : Serializable
