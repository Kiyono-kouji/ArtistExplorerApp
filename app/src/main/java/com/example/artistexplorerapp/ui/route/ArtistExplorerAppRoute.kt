package com.example.artistexplorerapp.ui.route

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.artistexplorerapp.ui.view.AlbumDetailView
import com.example.artistexplorerapp.ui.view.ArtistExplorerAppView
import com.example.artistexplorerapp.ui.viewmodel.ArtistExplorerAppViewModel

enum class AppView(val title: String) {
    ArtistView("Artist"),
    AlbumDetail("Album")
}

@Composable
fun ArtistExplorerAppRoute() {
    val navController = rememberNavController()
    val viewModel: ArtistExplorerAppViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val artistInfo by viewModel.artistInfo.collectAsState()
    val albums by viewModel.albums.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val topBarTitle = when {
        currentRoute == AppView.ArtistView.name -> artistInfo?.name ?: "Artist"
        currentRoute?.startsWith(AppView.AlbumDetail.name) == true -> {
            val albumId = navBackStackEntry?.arguments?.getString("albumId")
            albums.find { it.id == albumId }?.title ?: "Album"
        }
        else -> "Artist Explorer"
    }

    // Only show global loading/error for initial artist load (when artistInfo is null)
    val showGlobalLoading = isLoading && artistInfo == null
    val showGlobalError = errorMessage != null && artistInfo == null

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = if (showGlobalLoading) "Loading..."
                else if (showGlobalError) "Error"
                else topBarTitle
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                showGlobalLoading -> {
                    LoadingScreen()
                }
                showGlobalError -> {
                    ErrorScreen(errorMessage = errorMessage ?: "Unknown error")
                }
                else -> {
                    NavHost(
                        navController = navController,
                        startDestination = AppView.ArtistView.name
                    ) {
                        composable(route = AppView.ArtistView.name) {
                            ArtistExplorerAppView(
                                viewModel = viewModel,
                                onAlbumClick = { albumId ->
                                    navController.navigate("${AppView.AlbumDetail.name}/$albumId")
                                }
                            )
                        }
                        composable(route = "${AppView.AlbumDetail.name}/{albumId}") { backStackEntry ->
                            val albumId = backStackEntry.arguments?.getString("albumId") ?: return@composable
                            AlbumDetailView(viewModel = viewModel, albumId = albumId)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF282828))
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp),
                    color = Color(0xFFFFD54F),
                    strokeWidth = 4.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Loading...",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun ErrorScreen(errorMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF282828))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Error. Tidak ada koneksi internet",
                color = Color(0xFFFF5252),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1A1A1A)
        ),
        modifier = modifier
    )
}