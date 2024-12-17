package com.example.petpals.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Χρωματική Παλέτα
private val LightColorPalette = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    surface = BackgroundColor,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = TextColor,
    onSurface = TextColor
)

object AppIconsTheme {
    val iconSize = 20.dp
}

// Material Theme
@Composable
fun PetPalsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}