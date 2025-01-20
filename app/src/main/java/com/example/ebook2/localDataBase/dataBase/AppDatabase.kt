package com.example.ebook2.localDataBase.dataBase

import com.example.ebook2.Data_layer.Response.BookModels
import com.example.ebook2.localDataBase.dao.EBookDao
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [BookModels::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract  fun bookDao(): EBookDao
}