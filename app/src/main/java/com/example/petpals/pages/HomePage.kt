package com.example.petpals.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petpals.R
import com.example.petpals.data.Pet
import com.example.petpals.ui.theme.AppIconsTheme
import com.example.petpals.ui.theme.PetCard
import com.example.petpals.ui.theme.PetPalsTheme
import com.example.petpals.ui.theme.PrimaryButton
import com.example.petpals.ui.theme.ShadowCard
import com.example.petpals.ui.theme.TextColor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun HomePage() {
    // Scrollable state for the screen
    val scrollState = rememberScrollState()
    val recentPets = remember { mutableStateListOf<Pet>() }

    LoadPets(recentPets, 4)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .verticalScroll(scrollState)
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
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Recent pets
        Text(
            text = "Recent Pets",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        PetsGrid(
            pets = recentPets,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(48.dp))

        // Map
        Text(
            text = "Pet Adoption Centers",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        ShadowCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Map", color = TextColor)
        }
    }
}

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

@Composable
fun PetsGrid(pets: List<Pet>, modifier: Modifier = Modifier) {
    if (pets.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.error404),
                contentDescription = "PetPals Logo",
                modifier = Modifier

            )
            Text(
                text = "Oops... No pets were found",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
        ) {
            pets.chunked(2).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { pet ->
                        pet.age?.let {
                            pet.imageUrl?.let { it1 ->
                                PetCard(
                                    imageUrl = it1,
                                    petName = pet.name,
                                    petSpecies = pet.species,
                                    petAge = it
                                )
                            }
                        }
                    }
                    // Spacer for uneven rows
                    if (row.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            PrimaryButton(text = "Browse All Pets") {}
        }
    }
}

suspend fun fetchPets(limit: Long): List<Pet> {
    val db = FirebaseFirestore.getInstance()
    return try {
        // Fetch pets from Firestore collection "pets"
        val querySnapshot = db.collection("pets")
            .orderBy("uploadDate", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(limit) // Limit how many pets to fetch
            .get()
            .await()

        // Map to Pet objects
        querySnapshot.documents.mapNotNull { document ->
            document.toObject(Pet::class.java)?.apply {
                id = document.id.toInt() // ID used as the pet ID
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}

@Composable
fun LoadPets(recentPets: MutableList<Pet>, limit: Long) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            // Fetch the most recent pets from Firestore
            val fetchedPets = fetchPets(limit)
            recentPets.clear()
            recentPets.addAll(fetchedPets)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    PetPalsTheme {
        HomePage()
    }
}