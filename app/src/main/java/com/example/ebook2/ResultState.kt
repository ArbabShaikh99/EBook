package com.example.ebook2

import java.lang.Exception

sealed  class ResultState<out T> {
data class  Success<out T>(val data:T) :ResultState<T>()
data class  Error<T>(val exception: Throwable) :ResultState<T>()
    object  loading: ResultState<Nothing>()
}