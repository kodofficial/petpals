package com.example.petpals.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petpals.R
import com.example.petpals.data.Pet
import com.example.petpals.ui.theme.PetPalsTheme
import com.example.petpals.ui.theme.ShadowCard

@Composable
fun AdoptPage() {
    // Scrollable state for the screen
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 8.dp)
        ) {
            Text(
                text = "Adopt",
                style = MaterialTheme.typography.titleMedium,
            )
        }

        var searchQuery by remember { mutableStateOf("") }
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )

        val categories = listOf(
            Pair(R.drawable.dog, "Dog"),
            Pair(R.drawable.cat, "Cat"),
            Pair(R.drawable.bird, "Bird"),
            Pair(R.drawable.hamster, "Hamster"),
            Pair(R.drawable.rabbit, "Rabbit"),
            Pair(R.drawable.mouse, "Mouse")
        )

        Column(
            modifier = Modifier
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = "Filter by",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            categories.chunked(3).forEach { rowCategories ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowCategories.forEach { (icon, text) ->
                        ShadowCard(
                            modifier = Modifier
                                .weight(1f)
                                .height(87.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = icon),
                                    contentDescription = text,
                                    modifier = Modifier.size(32.dp),
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }

        Text(
            text = "Available pets",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))

        val pets = remember { mutableStateListOf<Pet>() }
        LoadPets(pets, 10)

        PetsGrid(
            pets = pets,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AdoptPagePreview() {
    PetPalsTheme {
        AdoptPage()
    }
}