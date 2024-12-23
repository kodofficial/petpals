package com.example.petpals.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ShadowCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    backgroundColor: Color = Color.White,
    elevation: Dp = 14.dp,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = shape,
                spotColor = PrimaryColor,
                ambientColor = PrimaryColor
            )
            .background(
                color = backgroundColor,
                shape = shape
            ),
        contentAlignment = contentAlignment
    ) {
        content()
    }
}

@Composable
fun PetCard(
    imageUrl: String,
    petName: String,
    petSpecies: String,
    petAge: Int
) {
    ShadowCard{
        Column {
            // Dynamic Pet Image
            AsyncImage(
                model = imageUrl,
                contentDescription = "Pet Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium), // Rounded corners
                contentScale = ContentScale.Crop // Crop to fit the aspect ratio
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "$petSpecies, $petAge years old",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = petName, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun PetCardPreview() {
    PetPalsTheme {
        PetCard("test", "test", "test", 14)
    }
}
