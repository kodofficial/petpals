package com.example.petpals.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petpals.R
import com.example.petpals.ui.theme.AppIconsTheme
import com.example.petpals.ui.theme.PetPalsTheme
import com.example.petpals.ui.theme.PrimaryButton
import com.example.petpals.ui.theme.ShadowCard
import com.example.petpals.ui.theme.TextColor

@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable._fulllogo),
            contentDescription = "PetPals Logo",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Welcoming text messages
        Text(
            text = "Welcome to PetPals",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Find your new lifelong companion here!",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Search bar
        var searchQuery by remember { mutableStateOf("") }
        ShadowCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 17.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(AppIconsTheme.iconSize),
                )
                Spacer(modifier = Modifier.width(15.dp))
                if (searchQuery.isEmpty()) {
                    Text(
                        text = "Search for your next pet...",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    textStyle = TextStyle(color = TextColor, fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth() // Ensures the text field takes up the remaining space
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Suggested for u
        Text(
            text = "Suggested for you",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Grid-like layout
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // First row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ShadowCard(
                    modifier = Modifier
                        .weight(1f) // Equal distribution of space
                        .aspectRatio(1f), // Make the box square
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Pet #1", color = TextColor)
                }

                ShadowCard(
                    modifier = Modifier
                        .weight(1f) // Equal distribution of space
                        .aspectRatio(1f), // Make the box square
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Pet #2", color = TextColor)
                }
            }

            // Second row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ShadowCard(
                    modifier = Modifier
                        .weight(1f) // Equal distribution of space
                        .aspectRatio(1f), // Make the box square
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Pet #3", color = TextColor)
                }

                ShadowCard(
                    modifier = Modifier
                        .weight(1f) // Equal distribution of space
                        .aspectRatio(1f), // Make the box square
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Pet #4", color = TextColor)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            PrimaryButton(text = "Browse All Pets") {}
        }

        /*
        Spacer(modifier = Modifier.height(48.dp))

        // Suggested for u
        Text(
            text = "Pet Adoption Centers",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Map
        ShadowCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Map", color = TextColor)
        }

         */
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    PetPalsTheme {
        HomePage()
    }
}