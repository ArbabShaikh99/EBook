package com.example.ebook2.UI_layer.Screen

import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.ebook2.R
import com.example.ebook2.UI_layer.Navigation.routes
import com.example.ebook2.ViewModel.BookViewModel
import com.example.ebook2.ui.theme.PureWhite
import com.example.ebook2.ui.theme.dark
import com.example.ebook2.ui.theme.lightgray


@Composable
fun AllBooksScreen(viewModel: BookViewModel= hiltViewModel() , navController: NavController) {

    val AllBookstate = viewModel.getAllBookState.collectAsState()
    val booklist = AllBookstate.value.data ?: emptyList()

//    LaunchedEffect(key1 = Unit) {
//        viewModel.getAllBooks()
//    }

    when{
        AllBookstate.value.isLoading ->{
           Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
               CircularProgressIndicator(color = dark, modifier = Modifier.size(80.dp))
           }

        }
        AllBookstate.value.data.isNotEmpty() ->{
            Log.d("wow", "Data is here ${AllBookstate.value.data}")



                LazyColumn (modifier = Modifier.fillMaxSize()
                    .background(lightgray)){
                    items(booklist){ index ->
                        Spacer(modifier = Modifier.height(18.dp))

                        Books(
                            title = index.bookName,
                            bookImage = index.bookImage,
                            bookAuthor = index.Description,
                            onItemClick = {
                                navController.navigate(routes.pdfView(
                                    pdfUrl = index.bookpdfUrl,
                                    bookname = index.bookName,
                                    authorName = index.bookAuthor,
                                    BookImage = index.bookImage,
                                    id = index.bookID))
                            }
                        )

                    }
                }

        }
        AllBookstate.value.error !=null ->{
            Text("Error is Here: ${AllBookstate.value.error}")
        }
    }
}

@Preview
@Composable
fun Books(
    title: String="Android developer",
    bookImage: String ="https://developer.okta.com/assets-jekyll/blog/tutorial-kotlin-beginners-guide/kotlin-logo-social-21c8518b19eb96d96f35e0057bb92b7e1281a24820e0fa09e39c42f184bd7faa.png",
    bookAuthor: String = "Author ",
    onItemClick:() -> Unit ={}
) {

//val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(3.dp) // Outer padding for spacing around the card
          // .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)) // Border with rounded corners
           .clip(RoundedCornerShape(8.dp)) // Ensures content inside card respects rounded corners
            .background(lightgray)
            .clickable {
                onItemClick() }
    ) {


            Row (modifier = Modifier
                .fillMaxWidth()
                .background(dark),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically)
            {
             //   val testImage = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"


//                    AsyncImage(
//                       model ="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png" ,
//                        contentDescription = title,
//                        error = painterResource(id = R.drawable.ic_launcher_background),
//                        modifier = Modifier
//                            .size(100.dp)
//                    )
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
                        .size(140.dp)
                        .padding(top= 6.dp , bottom = 6.dp)
                        .width(90.dp)
                )
                Log.d("@book", "Books: $bookImage")
                Column(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly)
                {
                    Text(text = title
                        , maxLines = 1,
                        color = PureWhite,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = bookAuthor,
                        color = PureWhite,
                        maxLines = 3,
                        fontSize = 14.sp)
                }

        }
    }
}