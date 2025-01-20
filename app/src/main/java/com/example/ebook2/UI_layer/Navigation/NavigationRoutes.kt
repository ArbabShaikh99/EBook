package com.example.ebook2.UI_layer.Navigation

import kotlinx.serialization.Serializable



@Serializable
sealed class routes {

@Serializable
  object Home

  @Serializable
  data class pdfView(
    val pdfUrl: String,
    val bookname :String,
    val authorName :String,
    val BookImage : String,
    val id : Int,
  )
  @Serializable
  object BookmarkScreenRoute

  @Serializable
  data class BookByCategory(
    val category: String
  )


}