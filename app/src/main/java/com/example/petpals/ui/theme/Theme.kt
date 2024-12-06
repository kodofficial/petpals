package com.example.petpals.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Χρωματική Παλέτα
private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColor, // Μπορείς να προσθέσεις διαφορετικό χρώμα αν χρειαστεί.
    secondary = SecondaryColor,
    background = BackgroundColor,
    surface = BackgroundColor,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = TextColor,
    onSurface = TextColor
)

// Material Theme
@Composable
fun PetPalsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}