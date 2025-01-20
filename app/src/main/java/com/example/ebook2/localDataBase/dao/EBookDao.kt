package com.example.ebook2.localDataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ebook2.Data_layer.Response.BookModels
import kotlinx.coroutines.flow.Flow

@Dao
interface EBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertBook(book : BookModels)

    @Query("SELECT * FROM Book_Table ")
     fun getAllBookList(): Flow<List<BookModels>>

    @Query("DELETE  FROM Book_Table WHERE bookID= :bookId")
    suspend fun DeleteBook(bookId :Int)

    @Query("SELECT * FROM Book_Table WHERE bookID= :bookId")
    suspend fun getBookById(bookId :Int): BookModels?

}