package com.example.petpals.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = TextColor
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = TextColor // Για τα ονόματα των sections ανά screen
    ),
    titleSmall = TextStyle(
        fontSize = 12.sp,
        color = Color.Gray // Για τον υπότιτλο στο homescreen
    ),

    bodyLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = TextColor // Για τα ονόματα των ζώων στις αγγελιες
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        color = TextColor // Για τα περιεχόμενα των ζώων στις αγγελιες
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        color = Color.Gray // Για τον υπότιτλο στα ζώα στις αγγελιες
    ),

    labelLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color.White // Χρώμα για τα κουμπιά
    )
)