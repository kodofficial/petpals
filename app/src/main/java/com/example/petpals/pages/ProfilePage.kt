package com.example.petpals.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petpals.data.*
import com.example.petpals.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    currentUser: User,
    userPets: List<Pet>,
    onUpdateUserInfo: (User) -> Unit,
    onEditPet: (Pet) -> Unit,
    onDeletePet: (Pet) -> Unit,
    navController: NavController
) {
    var userName by remember { mutableStateOf(currentUser.userName) }
    var userEmail by remember { mutableStateOf(currentUser.email) }
    var userFirstName by remember { mutableStateOf(currentUser.firstName) }
    var userLastName by remember { mutableStateOf(currentUser.lastName) }
    var userPassword by remember { mutableStateOf(currentUser.password) }

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) },
        topBar = {
            TopAppBar(
                title = { Text("My Profile", style = MaterialTheme.typography.titleMedium) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // User Information Section
            Text("Edit Information", style = MaterialTheme.typography.bodyLarge)

            Row (
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = userFirstName,
                        onValueChange = { userFirstName = it },
                        label = {
                            Text(
                                "First Name",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = userLastName,
                        onValueChange = { userLastName = it },
                        label = {
                            Text(
                                "Last Name",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Username", color = MaterialTheme.colorScheme.onBackground) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = userEmail,
                onValueChange = { userEmail = it },
                label = { Text("Email", color = MaterialTheme.colorScheme.onBackground) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                label = { Text("Password", color = MaterialTheme.colorScheme.onBackground) },
                modifier = Modifier.fillMaxWidth()
            )
            PrimaryButton(
                text = "Save Information",
                onClick = {
                    val updatedUser = User(
                        firstName = userFirstName,
                        lastName = userLastName,
                        userName = userName,
                        email = userEmail,
                        password = userPassword
                    )
                    onUpdateUserInfo(updatedUser)
                },
                modifier = Modifier.fillMaxWidth()
            )

            // User's Pets Section
            Text("My Pets", style = MaterialTheme.typography.titleMedium)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                userPets.forEach { pet ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Row (
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = pet.name,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = pet.species,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray)
                            }
                            Text(text = "${pet.age} years old", style = MaterialTheme.typography.bodyMedium)
                            Text(text = pet.location ?: "Unknown location", style = MaterialTheme.typography.bodyMedium)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SecondaryButton(
                                    text = "Edit",
                                    onClick = { onEditPet(pet) },
                                    modifier = Modifier.weight(1f)
                                )
                                Button(
                                    onClick = { onDeletePet(pet) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                    shape = MaterialTheme.shapes.medium,
                                    modifier = Modifier.weight(1f),
                                ) {
                                    Text("Delete", style = MaterialTheme.typography.labelLarge)
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    PetPalsTheme {
        val navController = rememberNavController()

        ProfilePage(
            currentUser = User(
                firstName = "John",
                lastName = "Doe",
                userName = "johndoe",
                email = "john.doe@example.com",
                password = "password123"
            ),
            userPets = listOf(
                Pet(
                    id = 1,
                    name = "Fluffy",
                    species = "Cat",
                    breed = "Siamese",
                    age = 3,
                    gender = "Female",
                    description = "Very friendly",
                    imageUrl = "image_url_placeholder",
                    uploadDate = System.currentTimeMillis(),
                    location = "New York"
                ),
                Pet(
                    id = 2,
                    name = "Whisper",
                    species = "Dog",
                    breed = "Siamese",
                    age = 3,
                    gender = "Female",
                    description = "Very friendly",
                    imageUrl = "image_url_placeholder",
                    uploadDate = System.currentTimeMillis(),
                    location = "New York"
                )
                // Add more pets if needed
            ),
            onUpdateUserInfo = { /* do nothing */ },
            onEditPet = { /* do nothing */ },
            onDeletePet = { /* do nothing */ },
            navController = navController
        )
    }
}