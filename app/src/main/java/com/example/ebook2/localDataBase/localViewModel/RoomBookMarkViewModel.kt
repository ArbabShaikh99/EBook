package com.example.ebook2.localDataBase.localViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebook2.Data_layer.Response.BookModels
import com.example.ebook2.localDataBase.dao.EBookDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomBookMarkViewModel @Inject constructor( private  val bookDao : EBookDao):ViewModel() {

      private val _bookmark = MutableStateFlow(false)
       val  bookmark = _bookmark.asStateFlow()

    private val _getBookList = MutableStateFlow<List<BookModels>>(emptyList())
    val getBookList = _getBookList.asStateFlow()

    init {
        getAllBookmarkList()
    }

    fun bookMarkBook(book : BookModels){
        viewModelScope.launch {
            bookDao.InsertBook(book)
        }
    }

    fun deleteBook(id :Int){
        viewModelScope.launch {
            bookDao.DeleteBook(id)
        }
    }

    fun getAllBookmarkList() {
        viewModelScope.launch {
            bookDao.getAllBookList().collect {
                _getBookList.value = it
            }
        }
    }
//    fun checkBookmarked(id: Int) {
//        viewModelScope.launch {
//            if(bookDao.getBookById(id) != null){
//                bookmark.value = true
//            }else{
//                bookmark.value = false
//            }
//        }
//    }
fun toggleBookmark(id: Int) {
    viewModelScope.launch {
        if (bookDao.getBookById(id) != null) {
            bookDao.DeleteBook(id) // Remove bookmark
            _bookmark.value = false
        } else {
            bookDao.DeleteBook(id) // Add bookmark
            _bookmark.value = true
        }
    }
}


}