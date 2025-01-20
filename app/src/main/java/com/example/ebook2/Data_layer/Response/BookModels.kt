package com.example.ebook2.Data_layer.Response

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Book_Table")
data class BookModels(
    @PrimaryKey(autoGenerate = true) val bookID: Int = 0,
    val bookAuthor :String ="",
    val bookImage : String ="" ,
    val bookName :String ="",
    val bookpdfUrl : String = "",
    val category : String ="",
    val Description :String =""
)