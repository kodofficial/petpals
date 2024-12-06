package com.example.petpals.ui.theme

import android.annotation.SuppressLint

class PrimaryButton {
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material.Button
    import androidx.compose.material.ButtonDefaults
    import androidx.compose.material.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp

    @SuppressLint("NotConstructor")
    @Composable
    fun PrimaryButton(text: String, onClick: () -> Unit) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = PrimaryColor,
                contentColor = Color.White
            )
        ) {
            Text(text = text, style = MaterialTheme.typography.button)
        }
    }
}