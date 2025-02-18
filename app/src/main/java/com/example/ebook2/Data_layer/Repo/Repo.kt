package com.example.ebook2.Data_layer.Repo


import com.example.ebook2.Data_layer.Response.BookCategoryModels
import com.example.ebook2.Data_layer.Response.BookModels
import com.example.ebook2.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose


class Repo @Inject constructor(private val firebaseDatabase: FirebaseDatabase) {

    suspend fun getAllBook (): Flow<ResultState<List<BookModels>>> =  callbackFlow {

        trySend(ResultState.loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items :List<BookModels> = emptyList()

                items = snapshot.children.map{ value ->
                    value.getValue<BookModels>()!!
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }
        }
                                           //  addValueEventListener(   // argument  object  )
        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose{
            firebaseDatabase.reference.child("Books").removeEventListener(valueEvent)
            close()
        }

    }

    suspend fun getAllCategorys(): Flow<ResultState<List<BookCategoryModels>>> = callbackFlow{
        trySend(ResultState.loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items :List<BookCategoryModels> = emptyList()
                items = snapshot.children.map{ value ->
                    value.getValue<BookCategoryModels>()!!
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }
        }

        firebaseDatabase.reference.child("BookCategory").addValueEventListener(valueEvent)
        awaitClose{
            firebaseDatabase.reference.child("BookCategory").removeEventListener(valueEvent)
            close()
        }
    }

    suspend fun getBookByCategory(category :String) : Flow<ResultState<List<BookModels>>> = callbackFlow {

        trySend(ResultState.loading)
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bookCategory: List<BookModels> = snapshot.children.mapNotNull {
                    it.getValue<BookModels>()
                }.filter {
                    it.category == category
                }
                trySend(ResultState.Success(bookCategory))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(exception = error.toException()))
            }
        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)
        awaitClose{
            firebaseDatabase.reference.child("Books").removeEventListener(valueEvent)
            close()
        }
    }

}