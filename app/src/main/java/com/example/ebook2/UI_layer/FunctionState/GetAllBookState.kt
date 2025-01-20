package com.example.ebook2.UI_layer.FunctionState

import com.example.ebook2.Data_layer.Response.BookModels

data class GetAllBookStates(
    val isLoading : Boolean = false,
    val data : List<BookModels> = emptyList(),
    val error : Throwable ?= null
)