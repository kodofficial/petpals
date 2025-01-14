package com.example.petpals.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petpals.data.*
import com.example.petpals.ui.components.*
import com.example.petpals.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    currentUser: User,
    userPets: List<Pet>,
    onUpdateUserInfo: (User) -> Unit,
    onEditPet: (Pet) -> Unit,
    onDeletePet: (Pet) -> Unit
) {
    var userName by remember { mutableStateOf(currentUser.userName) }
    var userEmail by remember { mutableStateOf(currentUser.email) }
    var userFirstName by remember { mutableStateOf(currentUser.firstName) }
    var userLastName by remember { mutableStateOf(currentUser.lastName) }
    var userPassword by remember { mutableStateOf(currentUser.password) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", style = MaterialTheme.typography.titleLarge) }
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
            Text("User Information", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(
                value = userFirstName,
                onValueChange = { userFirstName = it },
                label = { Text("First Name", color = MaterialTheme.colorScheme.onBackground) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = userLastName,
                onValueChange = { userLastName = it },
                label = { Text("Last Name", color = MaterialTheme.colorScheme.onBackground) },
                modifier = Modifier.fillMaxWidth()
            )
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
                }
            )

            // User's Pets Section
            Text("My Pets", style = MaterialTheme.typography.titleLarge)
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
                            Text(text = pet.name, style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Species: ${pet.species}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Location: ${pet.location ?: "Unknown"}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Age: ${pet.age ?: "Unknown"}", style = MaterialTheme.typography.bodyMedium)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                SecondaryButton(
                                    text = "Edit",
                                    onClick = { onEditPet(pet) }
                                )
                                Button(
                                    onClick = { onDeletePet(pet) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text("Delete", style = MaterialTheme.typography.labelLarge)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
