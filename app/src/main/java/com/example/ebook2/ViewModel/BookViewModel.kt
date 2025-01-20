package com.example.ebook2.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebook2.Data_layer.Repo.Repo
import com.example.ebook2.ResultState
import com.example.ebook2.UI_layer.FunctionState.GetAllBookStates
import com.example.ebook2.UI_layer.FunctionState.GetAppCategoryState
import com.example.ebook2.UI_layer.FunctionState.GetBookByCategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val repo: Repo) : ViewModel(){


  private  val _getAllBookState  = MutableStateFlow(GetAllBookStates())
    val getAllBookState  = _getAllBookState.asStateFlow()

    private val _getAllBookCategoryState = MutableStateFlow(GetAppCategoryState())
    val getAllBookCategoryState = _getAllBookCategoryState.asStateFlow()

    private val _getBookByCategoryState = MutableStateFlow(GetBookByCategoryState())
    val getBookByCategoryState = _getBookByCategoryState.asStateFlow()

    init {
        getAllBooks()
        getAllCategory()
    }

    fun getBooksByCategory(category:String){
        viewModelScope.launch (Dispatchers.IO){
            repo.getBookByCategory(category).collect{
                when(it){
                    is ResultState.loading ->{
                        _getBookByCategoryState.value = GetBookByCategoryState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _getBookByCategoryState.value = GetBookByCategoryState(isLoading = false, data = it.data)
                    }
                    is ResultState.Error ->{
                        _getBookByCategoryState.value = GetBookByCategoryState(isLoading = false, error = it.exception)

                    }
                }
            }
        }
    }

    fun getAllBooks() {
        viewModelScope.launch (Dispatchers.IO){
            repo.getAllBook().collect {
                when(it){
                   is ResultState.loading ->{
                       _getAllBookState.value = GetAllBookStates(isLoading = true)
                   }
                    is ResultState.Success ->{
                        _getAllBookState.value = GetAllBookStates(isLoading = false, data = it.data)
                    }
                    is ResultState.Error -> {
                        _getAllBookState.value = GetAllBookStates(isLoading =false , error = it.exception)
                    }
                }
            }
        }
    }

    fun getAllCategory(){
        viewModelScope.launch (Dispatchers.IO){
            repo.getAllCategorys().collect{
                when(it){
                    is ResultState.loading ->{
                        _getAllBookCategoryState.value = GetAppCategoryState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _getAllBookCategoryState.value = GetAppCategoryState(isLoading = false, data = it.data)
                    }
                    is ResultState.Error ->{
                        _getAllBookCategoryState.value = GetAppCategoryState(isLoading =false , error = it.exception)
                    }
                }
            }
        }
    }


}