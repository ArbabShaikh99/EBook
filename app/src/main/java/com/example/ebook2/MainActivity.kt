package com.example.ebook2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ebook2.UI_layer.Navigation.AppNavigation
import com.example.ebook2.UI_layer.TabSetUp.TabBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            EBook2Theme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                   Box(modifier = Modifier.padding(innerPadding)){
//                       TabBar()
//                   }
//                }
//            }
//        }
//    }
//}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}