package com.example.ebook2.UI_layer.Screen

import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.ebook2.Data_layer.Response.BookModels
import com.example.ebook2.localDataBase.localViewModel.RoomBookMarkViewModel
import com.example.ebook2.ui.theme.dark
import com.example.ebook2.ui.theme.lightgray
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import kotlinx.coroutines.delay

@Composable
fun PDFViewScreen(
    pdfUrl: String ,
    bookName:String,
   authorName :String ,
    bookImage: String,
    id :Int,
  navController: NavController,
  roomBookMarkViewModel: RoomBookMarkViewModel) {

    val context = LocalContext.current
     val bookmarkCheck =  roomBookMarkViewModel.bookmark.collectAsState()

    // State to track loading progress
    val loadingProgress = remember { mutableStateOf(0) }
    val isLoading = remember { mutableStateOf(true) }

    // Mock PDF loading process (replace with actual logic if supported by library)
    LaunchedEffect(pdfUrl) {
        for (progress in 0..100) {
            delay(50) // Simulate loading delay
            loadingProgress.value = progress
        }
        isLoading.value = false // PDF loading complete
    }
        // save back entry
    val previousRoute = navController.previousBackStackEntry?.destination?.route

    Column(modifier = Modifier.fillMaxSize()
        .background(lightgray))
    {
        TopBarPdfView(
            bookName+" By " +authorName,
            previousRoute != null,
            onClick = {
                navController.navigateUp()
            },
            bookmarkCheck,
            onClickBookMark = {
                roomBookMarkViewModel.bookMarkBook(
                    BookModels(
                        bookImage = bookImage,
                        bookAuthor = authorName,
                        bookName = bookName,
                        bookpdfUrl = pdfUrl,

                    )
                )
                roomBookMarkViewModel.toggleBookmark(id =id)

                Toast.makeText(context, "Save Bookmark Successfuly", Toast.LENGTH_LONG).show()
            },


        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(lightgray),
           contentAlignment = Alignment.Center,
        ) {
            if (isLoading.value) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Circular Progress Indicator
                    CircularProgressIndicator(
                        progress = loadingProgress.value / 100f,
                        modifier = Modifier.size(70.dp),
                        color = dark,
                        strokeWidth = 4.dp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Loading Percentage
                    Text(
                        text = "${loadingProgress.value}%",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = dark
                    )
                }
            } else {
                // Show PDF once loaded
//            AndroidView(
//                factory = { context ->
//                    WebView(context).apply {
//                        // Enable JavaScript if needed
//                        settings.javaScriptEnabled = true
//                        // Load the book URL
//                        loadUrl(pdfUrl)
//
//                    }
//                },
//                update = { webView ->
//                    // Update the webView if necessary
//                    webView.loadUrl(pdfUrl)
//                },
//                modifier = Modifier
//                    .fillMaxSize()
                    //.padding(it) // Fill the available space
           // )
                val pdfState = rememberVerticalPdfReaderState(
                    resource = ResourceType.Remote(pdfUrl),
                    isZoomEnable = true
                )

                VerticalPDFReader(
                    state = pdfState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Gray)
                )
            }
        }
    }


}


@Composable
fun  TopBarPdfView(
    headerName: String,
    isBackButtonShow: Boolean,
    onClick: () -> Unit = {},
    bookMark: State<Boolean>,
    onClickBookMark:() ->Unit ={}
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(dark,
               // shape = RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 32.dp)
            )
            .height(116.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .weight(1f),
                   // .padding(end = 20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {


                if (isBackButtonShow) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(38.dp)
                            .padding(top = 8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onClick()
                            }
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = headerName, style = TextStyle(
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    ),modifier = Modifier.padding(top = 8.dp,end = 30.dp)
                )
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (bookMark.value) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "bookmark",
                    tint = Color.Black,
                    modifier = Modifier
                        //.padding(start = 40.dp)
                        .size(40.dp)
                        .clickable {
                            onClickBookMark()
                        }
                )
            }
        }
    }
}
