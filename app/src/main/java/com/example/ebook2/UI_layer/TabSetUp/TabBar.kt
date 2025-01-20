package com.example.ebook2.UI_layer.TabSetUp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ReadMore
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ebook2.R
import com.example.ebook2.UI_layer.Navigation.routes
import com.example.ebook2.UI_layer.Screen.AllBooksScreen
import com.example.ebook2.UI_layer.Screen.Books
import com.example.ebook2.UI_layer.Screen.CategoryScreen
import com.example.ebook2.ViewModel.BookViewModel
import com.example.ebook2.ui.theme.dark
import com.example.ebook2.ui.theme.lightgray
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TabBar(navController: NavHostController) {

    val viewModel: BookViewModel = hiltViewModel()
    val list = listOf(
        TabItem("Category", Icons.Rounded.Category),
        TabItem("All Books", Icons.Rounded.Book)
    )
    val pagerState = rememberPagerState(pageCount = { list.size })
    val scope = rememberCoroutineScope()


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor =lightgray ,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {

                Row(
                    verticalAlignment = Alignment.CenterVertically // Align icon and text properly
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Menu, // Replace with your desired icon
                        contentDescription = "App Icon",
                        tint = dark,
                        modifier = Modifier
                            .size(39.dp) // Icon size
                            .padding(end = 8.dp) // Space between icon and text
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        "BookNest",
                        color = dark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 29.sp
                    )
                }
            }, actions = {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Book",
                    tint = dark,
                    modifier = Modifier.size(40.dp).padding(4.dp).clickable {
                     navController.navigate(routes.BookmarkScreenRoute)
                    }
                )
            }
        )
    }) { innerPadding ->

        Column(modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
            .background(lightgray)) {
            Spacer(modifier = Modifier.height(25.dp))
            TabRow(

                selectedTabIndex = pagerState.currentPage,
             modifier = Modifier.fillMaxWidth(),
                // indcator color define here----
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = dark, // Define your desired color here
                        height = 3.dp // Optional: Define the thickness of the indicator line
                    )
                }

            ) {
                list.forEachIndexed { index, TabItem ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        modifier = Modifier.fillMaxWidth()
                            .background(lightgray),
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                        icon = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically, // Ensure the icon and text are vertically aligned
                                horizontalArrangement = Arrangement.Center // This centers them horizontally
                            ) {
                                Icon(
                                    imageVector = TabItem.icon,
                                    contentDescription = null,
                                    tint = dark,
                                    modifier = Modifier
                                        .size(31.dp)
                                        .padding(end = 4.dp) // Optional: Add space between icon and text
                                )
                                Text(
                                    text = TabItem.name,
                                    color = dark,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

//                        text = { Text(text = TabItem.name, color = dark) },
//                        icon = {
//                            Icon(
//                                imageVector = TabItem.icon, contentDescription = null,
//                                tint = dark
//                            )
//                        }
                    )
                }
            }
            HorizontalPager(state = pagerState) {
                when (it) {
                    0 -> CategoryScreen(viewModel, navController = navController)
                    1 -> AllBooksScreen(navController = navController)
                    //   1 -> AllBooksScreen( navController = navController)
//                0 -> Category(navController = navController)
//                1 -> AllBooksScreen(navController = navController)

                }
            }
        }

    }

}
data class  TabItem(
    val name : String,
    val icon : ImageVector
)