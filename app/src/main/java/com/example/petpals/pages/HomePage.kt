package com.example.petpals.pages


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petpals.AuthState
import com.example.petpals.PetViewModel
import com.example.petpals.R
import com.example.petpals.ui.theme.PetPalsTheme
import com.example.petpals.ui.theme.PetsGrid
import com.example.petpals.ui.theme.SearchBar
import com.example.petpals.ui.theme.ShadowCard
import com.example.petpals.ui.theme.TextColor
import com.example.petpals.AuthViewModel
import com.example.petpals.PetPalsScreens
import com.example.petpals.ui.theme.BottomNavBar

@Composable
fun HomePage(modifier: Modifier = Modifier, viewModel: PetViewModel, navController: NavController, authViewModel: AuthViewModel) {
    // Scrollable state for the screen
    val scrollState = rememberScrollState()
    val pets by viewModel.filteredPets.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate(PetPalsScreens.Login.name)
            else -> Unit
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                pets = pets,
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
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    PetPalsTheme {

    }
}