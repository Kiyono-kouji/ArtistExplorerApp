package com.example.artistexplorerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.artistexplorerapp.ui.route.ArtistExplorerAppRoute
import com.example.artistexplorerapp.ui.theme.ArtistExplorerAppTheme
import com.example.artistexplorerapp.ui.view.AlbumDetailView
import com.example.artistexplorerapp.ui.view.ArtistExplorerAppView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtistExplorerAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    ArtistExplorerAppView(
//                        modifier = Modifier
//                            .padding(innerPadding)
//                    )
//                }
//                AlbumDetailView()
                ArtistExplorerAppRoute()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtistExplorerAppTheme {
        Greeting("Android")
    }
}