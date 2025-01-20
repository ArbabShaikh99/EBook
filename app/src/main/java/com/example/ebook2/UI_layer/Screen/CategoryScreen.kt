package com.example.ebook2.UI_layer.Screen

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.ebook2.R
import com.example.ebook2.UI_layer.Navigation.routes
import com.example.ebook2.ViewModel.BookViewModel
import com.example.ebook2.ui.theme.dark

@Composable
fun CategoryScreen (viewModel: BookViewModel , navController: NavController){

    val categoryState = viewModel.getAllBookCategoryState.collectAsState()
    val data = categoryState.value.data?: emptyList()

    val context = LocalContext.current


    when{
        categoryState.value.isLoading ->{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(color = dark,modifier = Modifier.size(80.dp))
            }
        }
        categoryState.value.data.isNotEmpty()  ->{

            Column(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Number of columns
                    modifier = Modifier,
                   // contentPadding = PaddingValues(8.dp)
                ) {
                    items(data){

                        CategoryItems(
                            it.categoryImageUrl , it.name,
                            onClick = {
                                navController.navigate(routes.BookByCategory(category = it.name))
                            }
                        )

                    }
                }
            }
        }
        categoryState.value.error !=null ->{
            Text("Error is Here ")
        }
    }


}


@Composable
fun CategoryItems(
    bookImage :String,
    title:String,
    onClick: () -> Unit
){

    Card(
        modifier = Modifier
            .height(208.dp)
            .width(180.dp)
            .padding(8.dp)
            .clickable {
                onClick()
            }
    ) {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(dark),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            ){
            AndroidView(
                factory = { context ->
                    ImageView(context).apply {
                        Glide.with(context)
                            .load(bookImage)
                            .placeholder(R.drawable.ic_launcher_foreground) // Optional placeholder
                            .error(R.drawable.ic_launcher_background) // Optional error fallback
                            .into(this)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(8.dp)
            )
         Spacer(modifier = Modifier.height(0.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }


    }
}