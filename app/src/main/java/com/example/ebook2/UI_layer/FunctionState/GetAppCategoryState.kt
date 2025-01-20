package com.example.ebook2.UI_layer.FunctionState

import com.example.ebook2.Data_layer.Response.BookCategoryModels
import com.example.ebook2.Data_layer.Response.BookModels

data class GetAppCategoryState(
    val isLoading : Boolean =  false,
    val data : List<BookCategoryModels> = emptyList(),
    val error : Throwable ? =null
)
