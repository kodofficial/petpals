package com.example.petpals.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.petpals.data.User
import com.example.petpals.data.Pet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    currentUser: User, // User object
    userPets: List<Pet>, // List of user's pets
    onUpdateUserInfo: (User) -> Unit, // Update user information
    onEditPet: (Pet) -> Unit, // Edit pet
    onDeletePet: (Pet) -> Unit // Delete pet
) {
    // States for user information
    var userName by remember { mutableStateOf(currentUser.userName) }
    var userEmail by remember { mutableStateOf(currentUser.email) }
    var userFirstName by remember { mutableStateOf(currentUser.firstName) }
    var userLastName by remember { mutableStateOf(currentUser.lastName) }
    var userPassword by remember { mutableStateOf(currentUser.password) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // User Information Section
            Text("User Information", style = MaterialTheme.typography.headlineMedium)
            OutlinedTextField(
                value = userFirstName,
                onValueChange = { userFirstName = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = userLastName,
                onValueChange = { userLastName = it },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = userEmail,
                onValueChange = { userEmail = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
            )
            Button(
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
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Save Information")
            }

            // User's Pets Section
            Text("My Pets", style = MaterialTheme.typography.headlineMedium)
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(userPets.size) { index ->
                    val pet = userPets[index]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(text = pet.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Species: ${pet.species}")
                            Text(text = "Location: ${pet.location ?: "Unknown"}")
                            Text(text = "Age: ${pet.age ?: "Unknown"}")

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = { onEditPet(pet) }
                                ) {
                                    Text("Edit")
                                }
                                Button(
                                    onClick = { onDeletePet(pet) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
