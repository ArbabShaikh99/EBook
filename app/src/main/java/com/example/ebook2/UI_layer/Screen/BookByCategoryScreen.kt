package com.example.ebook2.UI_layer.Screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebook2.UI_layer.Navigation.routes
import com.example.ebook2.UI_layer.Screen.Common.TopBar
import com.example.ebook2.ViewModel.BookViewModel
import com.example.ebook2.ui.theme.dark

@Composable
fun BookByCategoryScreen(
    bookViewModel: BookViewModel = hiltViewModel() ,
    navController: NavController,
     categoryName :String) {

    Log.d("error", "category: ${categoryName}")

LaunchedEffect(Unit) {
    bookViewModel.getBooksByCategory(categoryName)
}

    val AllBookByCategorystate = bookViewModel.getBookByCategoryState.collectAsState()
    val categoryDataList =  AllBookByCategorystate.value.data



    when{
        AllBookByCategorystate.value.isLoading ->{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = dark, modifier = Modifier.size(60.dp))
            }
        }
        AllBookByCategorystate.value.data.isNotEmpty() -> {

            Log.d("error", "BookByCategory: ${AllBookByCategorystate.value.data}")


            val previousRoute = navController.previousBackStackEntry?.destination?.route


            Column(modifier = Modifier.fillMaxSize()) {

                TopBar("All $categoryName Books",
                    previousRoute != null,
                    onClick = {
                        navController.navigateUp()
                    })

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {
                    items(categoryDataList) {
                        Books(
                            title = it.bookName,
                            bookImage = it.bookImage,
                            bookAuthor = it.bookAuthor,

                            onItemClick = {
                                navController.navigate(
                                    routes.pdfView(
                                        pdfUrl = it.bookpdfUrl,
                                        bookname = it.bookName,
                                        authorName = it.bookAuthor,
                                        BookImage = it.bookImage,
                                        id = it.bookID
                                    )
                                )
                            }
                        )

                    }
                }
            }
        }
        AllBookByCategorystate.value.error !=null -> {
            Log.d("error", "BookByCategory -- Error: ${AllBookByCategorystate.value.error}")
            Box(modifier = Modifier.fillMaxSize()) {
                Text(AllBookByCategorystate.value.error.toString())
            }
        }
    }
}