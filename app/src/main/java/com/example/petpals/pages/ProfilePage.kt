package com.example.petpals.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petpals.data.*
import com.example.petpals.ui.theme.*
import com.example.petpals.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    currentUser: User?,
    navController: NavController
) {
    val userViewModel: UserViewModel = viewModel()

    var userName by remember { mutableStateOf(currentUser?.userName) }
    var userEmail by remember { mutableStateOf(currentUser?.email) }
    var userFirstName by remember { mutableStateOf(currentUser?.firstName) }
    var userLastName by remember { mutableStateOf(currentUser?.lastName) }
    var userPassword by remember { mutableStateOf(currentUser?.password) }

    var message by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }

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
                        value = userFirstName!!,
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
                        value = userLastName!!,
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
                value = userName!!,
                onValueChange = { userName = it },
                label = { Text("Username", color = MaterialTheme.colorScheme.onBackground) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = userEmail!!,
                onValueChange = { userEmail = it },
                label = { Text("Email", color = MaterialTheme.colorScheme.onBackground) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = userPassword!!,
                onValueChange = { userPassword = it },
                label = { Text("Password", color = MaterialTheme.colorScheme.onBackground) },
                modifier = Modifier.fillMaxWidth()
            )
            PrimaryButton(
                text = "Save Information",
                onClick = {
                    val updatedUser = User(
                        firstName = userFirstName!!,
                        lastName = userLastName!!,
                        userName = userName!!,
                        email = userEmail!!,
                        password = userPassword!!
                    )

                    try {
                        userViewModel.updateUserInfo(updatedUser)
                        message = "User information updated successfully."
                        isSuccess = true
                    } catch (e: Exception) {
                        message = "Failed to update user information: ${e.message}"
                        isSuccess = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = message,
                color = if (isSuccess) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    PetPalsTheme {
        val navController = rememberNavController()
        val mockUser = User("test", "test", "test1", "test1@gmail.com", "123456789")
        ProfilePage(currentUser = mockUser, navController = navController)
    }
}