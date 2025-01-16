package com.example.petpals.pages

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.petpals.PetViewModel
import com.example.petpals.data.Pet
import com.example.petpals.ui.theme.BottomNavBar
import com.example.petpals.ui.theme.PetPalsTheme
import com.example.petpals.ui.theme.PrimaryButton
import com.example.petpals.ui.theme.SecondaryButton
import kotlinx.coroutines.launch


class PostPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // PostScreen()
        }
    }
}

@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(3.dp)
            ) {
                Text(
                    text = selectedOption,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostScreenPreview() {
    PetPalsTheme {
        //PostScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(petViewModel: PetViewModel, navController: NavController) {
    // States για όλα τα πεδία εισαγωγής
    var name by rememberSaveable { mutableStateOf("") }
    var species by rememberSaveable { mutableStateOf("Dog") }
    var breed by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("Female") }
    var description by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var photoUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        photoUri = uri
    }

    val speciesOptions = listOf("Dog", "Cat", "Bird", "Hamster", "Rabbit", "Mouse")
    val genderOptions = listOf("Female", "Male")

    // Error or success message
    var message by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Post Pet for Adoption",
                        style = MaterialTheme.typography.titleMedium,
                        //color = MaterialTheme.colorScheme.onPrimary
                    )
                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary
//                )
            )
        },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        // Wrap the column in a vertical scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()), // Enable scrolling
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Όνομα
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text(
                        text = "Pet name",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row (
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.weight(1f)) { // Wrapping in Box to apply weight
                    DropdownMenuField(
                        label = "Species",
                        options = speciesOptions,
                        selectedOption = species,
                        onOptionSelected = { species = it }
                    )
                }

                Box(modifier = Modifier.weight(1f)) { // Wrapping in Box to apply weight
                    DropdownMenuField(
                        label = "Gender",
                        options = genderOptions,
                        selectedOption = gender,
                        onOptionSelected = { gender = it }
                    )
                }
            }

            // Ηλικία
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = {
                    Text(
                        text = "Age (in years)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Φυλή
            OutlinedTextField(
                value = breed,
                onValueChange = { breed = it },
                label = {
                    Text(
                        text = "Breed (optional)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Περιγραφή
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = {
                    Text(
                        text = "Description (optional)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Περιοχή
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = {
                    Text(
                        text = "Location (optional)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Επιλογή Φωτογραφίας
            Text(
                text = "Picture",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(4.dp) // Optional: for rounded corners
                    )
            ) {
                if (photoUri != null) {
                    AsyncImage(
                        model = photoUri,
                        contentDescription = "Pet Image",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = "No picture chosen",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            SecondaryButton(
                text = "Choose From Gallery",
                onClick = { photoPickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            )

            // Message Display
            Text(
                text = message,
                color = if (isSuccess) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )

            // Submit Button
            PrimaryButton(
                text = "Post",
                onClick = {
                    if (name.isNotBlank() && species.isNotBlank() && gender.isNotBlank() && age.isNotBlank() && photoUri != null) {
                        scope.launch {
                            val newPet = Pet(
                                name = name,
                                species = species,
                                breed = breed.ifBlank { null },
                                age = age.toInt(),
                                gender = gender,
                                description = description.ifBlank { null },
                                imageUrl = "",
                                uploadDate = System.currentTimeMillis().toLong(),
                                location = location.ifBlank { null }
                            )

                            val imageUrl = petViewModel.uploadImageAndGetUrl(photoUri!!)
                            if (imageUrl != null) {
                                newPet.imageUrl = imageUrl
                                petViewModel.addPet(newPet, photoUri!!)
                                message = "Pet Successfully Posted: ${newPet.name}"
                                isSuccess = true
                            } else {
                                message = "Error uploading image."
                                isSuccess = false
                            }
                        }
                    } else {
                        message = "Please Fill in All Required Fields."
                        isSuccess = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Preview
@Composable
fun PreviewPostPage() {

}

