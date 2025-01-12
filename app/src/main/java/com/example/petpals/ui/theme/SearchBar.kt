package com.example.petpals.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petpals.R

@Composable
fun SearchBar (
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ShadowCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 17.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(AppIconsTheme.iconSize)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text(
                            text = "Search for your next pet...",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        textStyle = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}