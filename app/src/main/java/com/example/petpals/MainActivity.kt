package com.example.petpals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowInsetsControllerCompat
import com.example.petpals.ui.theme.PetPalsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel : AuthViewModel by viewModels()
        window.statusBarColor = android.graphics.Color.TRANSPARENT // Make the status bar transparent
        window.navigationBarColor = android.graphics.Color.TRANSPARENT // Make the navigation bar transparent
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true  // Set status bar icons to dark
        }
        setContent {
                PetPalsTheme {
                    Navigation(modifier = Modifier, authViewModel)
                }
        }
    }
}