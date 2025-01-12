package com.example.petpals.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petpals.PetViewModel
import com.example.petpals.R
import com.example.petpals.ui.theme.PetPalsTheme
import com.example.petpals.ui.theme.PetsGrid
import com.example.petpals.ui.theme.PrimaryColor
import com.example.petpals.ui.theme.SearchBar
import com.example.petpals.ui.theme.ShadowCard

@Composable
fun AdoptPage(viewModel: PetViewModel) {
    // Scrollable state for the screen
    val scrollState = rememberScrollState()
    var isFilterByVisible by remember { mutableStateOf(false) }
    val pets by viewModel.filteredPets.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Adopt",
                style = MaterialTheme.typography.titleMedium,
            )
            Icon(
                painter = painterResource(id = R.drawable.filter_by_icon),
                contentDescription = "Filter by",
                modifier = Modifier
                    .clickable { isFilterByVisible = !isFilterByVisible }
            )
        }

        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.updateSearchQuery(it) }
        )

        val categories = listOf(
            Pair("\uD83D\uDC36", "Dog"),
            Pair("\uD83D\uDC31", "Cat"),
            Pair("\uD83E\uDD9C", "Bird"),
            Pair("\uD83D\uDC39", "Hamster"),
            Pair("\uD83D\uDC30", "Rabbit"),
            Pair("\uD83D\uDC2D", "Mouse")
        )

        // AnimatedVisibility for the filter section
        AnimatedVisibility(
            visible = isFilterByVisible,
            enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(vertical = 20.dp)
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
                            val isSelected = selectedCategory == text
                            ShadowCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(87.dp)
                                    .clickable {
                                        viewModel.updateSelectedCategory(if (isSelected) null else text)
                                    },
                                contentAlignment = Alignment.Center,
                                backgroundColor = Color.White,
                                borderColor = if (isSelected) PrimaryColor else null // Border stroke when selected
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = icon,
                                        fontSize = 22.sp
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
        }

        Text(
            text = "Available pets",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 20.dp)
        )

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
        AdoptPage(
            viewModel = TODO()
        )
    }
}