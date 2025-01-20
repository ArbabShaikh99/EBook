package com.example.ebook2.UI_layer.Navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ebook2.UI_layer.Screen.AllBooksScreen
import com.example.ebook2.UI_layer.Screen.BookByCategoryScreen
import com.example.ebook2.UI_layer.Screen.BookMarkScreen
import com.example.ebook2.UI_layer.Screen.PDFViewScreen
import com.example.ebook2.UI_layer.TabSetUp.TabBar
import com.example.ebook2.ViewModel.BookViewModel
import com.example.ebook2.localDataBase.localViewModel.RoomBookMarkViewModel

@Composable
fun AppNavigation () {

    val navController = rememberNavController()

    val bookViewModel : BookViewModel = hiltViewModel()
    val roomBookMarkViewModel : RoomBookMarkViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = routes.Home) {

        composable<routes.Home> {
            TabBar(navController)
        }

      composable<routes.pdfView> {
          val pdfOfBook =  it.toRoute<routes.pdfView>().pdfUrl
          val BookName = it.toRoute<routes.pdfView>().bookname
          val authorname = it.toRoute<routes.pdfView>().authorName
          val bookOfImage = it.toRoute<routes.pdfView>()
          val bookID = it.toRoute<routes.pdfView>().id
          PDFViewScreen(
              pdfUrl = pdfOfBook,
             bookName = BookName ,
              authorName = authorname,
              bookImage = bookOfImage.BookImage,
              id = bookID,
              navController,roomBookMarkViewModel)
      }

        composable<routes.BookByCategory> {
            val data =  it.toRoute<routes.BookByCategory>()

            BookByCategoryScreen(
                bookViewModel = bookViewModel,
                navController,
                categoryName = data.category)
        }
       composable<routes.BookmarkScreenRoute> {
       BookMarkScreen(roomBookMarkViewModel , navController)
                  }

    }

}