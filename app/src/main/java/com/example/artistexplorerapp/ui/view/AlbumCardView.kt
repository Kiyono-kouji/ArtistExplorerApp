package com.example.artistexplorerapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.artistexplorerapp.ui.model.AlbumModel

@Composable
fun AlbumCardView(
    album: AlbumModel,
    modifier: Modifier = Modifier,
    onCardClick : () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(180.dp)
            .height(260.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                AsyncImage(
                    model = album.imageURL,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = album.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${album.yearReleased} · ${album.genre ?: "Unknown"}",
                    color = Color(0xFFB0B0B0),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AlbumCardPreview() {
    AlbumCardView(
        album = AlbumModel(
            id = "1",
            artistId = "1",
            title = "Sob Rock",
            artist = "John Mayer",
            yearReleased = "2021",
            genre = "Indie",
            imageURL = "https://www.theaudiodb.com/images/media/album/thumb/sob_rock.jpg",
            description = ""
        )
    )
}