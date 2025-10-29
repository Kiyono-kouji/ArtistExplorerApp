package com.example.artistexplorerapp.ui.view

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.artistexplorerapp.ui.viewmodel.ArtistExplorerAppViewModel

@Composable
fun ArtistExplorerAppView(
    modifier: Modifier = Modifier,
    viewModel: ArtistExplorerAppViewModel = viewModel(),
    onAlbumClick: (String) -> Unit = {}
) {
    val artistInfo by viewModel.artistInfo.collectAsState()
    val artistAlbums by viewModel.albums.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF282828)),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Artist header - spans full width
        item(span = { GridItemSpan(2) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .background(Color(0xFF282828)),
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .size(380.dp)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    AsyncImage(
                        model = artistInfo?.imageURL,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 20.dp, bottom = 20.dp)
                    ) {
                        Text(
                            text = artistInfo?.name ?: "",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = artistInfo?.genre ?: "",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
        }

        // Albums title - spans full width
        item(span = { GridItemSpan(2) }) {
            Text(
                "Albums",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        // Album cards - 2 columns
        items(artistAlbums) { album ->
            AlbumCardView(
                album = album,
                modifier = Modifier.padding(horizontal = 8.dp),
                onCardClick = { onAlbumClick(album.id) }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun ArtistExplorerAppPreview(){
    ArtistExplorerAppView()
}