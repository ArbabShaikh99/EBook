package com.example.ebook2.UI_layer.Screen

import android.util.Log
import android.widget.ImageView
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.ebook2.R
import com.example.ebook2.UI_layer.Navigation.routes
import com.example.ebook2.UI_layer.Screen.Common.TopBar
import com.example.ebook2.localDataBase.localViewModel.RoomBookMarkViewModel
import com.example.ebook2.ui.theme.PureWhite
import com.example.ebook2.ui.theme.dark
import com.example.ebook2.ui.theme.lightgray

@Composable
fun BookMarkScreen(roomBookMarkViewModel: RoomBookMarkViewModel , navController: NavController) {


    val getAllBook = roomBookMarkViewModel.getBookList.collectAsState()
    val roombookmarkList = getAllBook.value
    val context = LocalContext.current

    val previousRoute = navController.previousBackStackEntry?.destination?.route

    Column(modifier = Modifier.fillMaxSize()) {

        TopBar(" Your Bookmark Books",
            previousRoute != null,
            onClick = {
                navController.navigateUp()
            })

        if (roombookmarkList.isNotEmpty()) {

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(roombookmarkList) {
                    BookMarkCard(
                        title = it.bookName,
                        bookImage = it.bookImage,
                        bookAuthor = it.bookAuthor,
                        onClick = {
                            navController.navigate(
                                routes.pdfView(
                                    pdfUrl = it.bookpdfUrl,
                                    bookname = it.bookName,
                                    authorName = it.bookAuthor,
                                    BookImage = it.bookImage,
                                    id = it.bookID
                                )
                            )
                        },
                        onClickDelte = {
                            roomBookMarkViewModel.deleteBook(
                                it.bookID
                            )
                            Toast.makeText(context, "Remove Book Successfully", Toast.LENGTH_LONG)
                                .show()
                        }
                    )
                }
            }
        }
        else{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
              Text("Your bookmark list is empty. Add some books!" ,
                  fontWeight = FontWeight.Normal,
                  fontSize = 18.sp)
            }
        }
    }
}
    @Preview
    @Composable
    fun BookMarkCard(
        title: String = "Android developer",
        bookImage: String = "https://developer.okta.com/assets-jekyll/blog/tutorial-kotlin-beginners-guide/kotlin-logo-social-21c8518b19eb96d96f35e0057bb92b7e1281a24820e0fa09e39c42f184bd7faa.png",
        bookAuthor: String = "Author ",
        onClick: () -> Unit = {},
        onClickDelte: () -> Unit = {}
    ) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(3.dp) // Outer padding for spacing around the card
                // .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)) // Border with rounded corners
                .clip(RoundedCornerShape(8.dp)) // Ensures content inside card respects rounded corners
                .background(lightgray)
                .clickable {
                    onClick()
                }
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(dark),
                   // .padding(8.dp), // Added padding for better spacing
            horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            )
            {
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
                    modifier = Modifier.size(140.dp)
                )
                Log.d("@book", "Books: $bookImage")
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    Text(
                        text = title, maxLines = 1,
                        color = PureWhite,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = bookAuthor,
                        color = PureWhite,
                        maxLines = 3,
                        fontSize = 14.sp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(end = 8.dp) ){
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                        .size(28.dp)
                            .clickable {
                                onClickDelte()
                            }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Remove ",
                        color = lightgray,
                        fontWeight = FontWeight.ExtraBold)
                }

            }  }
        }

