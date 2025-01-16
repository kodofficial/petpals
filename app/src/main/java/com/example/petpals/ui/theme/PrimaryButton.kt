package com.example.petpals.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: () -> Unit = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = text)
    }
}

