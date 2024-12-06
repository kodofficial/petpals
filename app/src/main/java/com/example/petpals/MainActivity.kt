package com.example.petpals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.petpals.ui.theme.PetPalsTheme
import com.example.petpals.ui.theme.PrimaryButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetPalsTheme {
                PrimaryButton(text = "Click Me") {
                }
            }
        }
    }
}