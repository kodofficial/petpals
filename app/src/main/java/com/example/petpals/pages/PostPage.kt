package com.example.petpals.pages

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.petpals.data.Pet
import com.example.petpals.ui.theme.PrimaryButton

class PostPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostScreen()
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedOption)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
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
    PostScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen() {
    // States για όλα τα πεδία εισαγωγής
    var name by rememberSaveable { mutableStateOf("") }
    var species by rememberSaveable { mutableStateOf("Σκύλος") }
    var breed by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("Θηλυκό") }
    var description by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var photoUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        photoUri = uri
    }

    val speciesOptions = listOf("Σκύλος", "Γάτα", "Κουνέλι", "Τρωκτικό", "Πτηνό", "Άλλο")
    val genderOptions = listOf("Θηλυκό", "Αρσενικό")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Δημιουργία Αγγελίας",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        // Wrap the column in a vertical scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Enable scrolling
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Όνομα
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text(
                        text = "Όνομα Κατοικιδίου",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Είδος
            DropdownMenuField(
                label = "Είδος",
                options = speciesOptions,
                selectedOption = species,
                onOptionSelected = { species = it }
            )

            // Φυλή
            OutlinedTextField(
                value = breed,
                onValueChange = { breed = it },
                label = {
                    Text(
                        text = "Φυλή (προαιρετικό)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Ηλικία
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = {
                    Text(
                        text = "Ηλικία (σε χρόνια, προαιρετικό)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Φύλο
            DropdownMenuField(
                label = "Φύλο",
                options = genderOptions,
                selectedOption = gender,
                onOptionSelected = { gender = it }
            )

            // Περιγραφή
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = {
                    Text(
                        text = "Περιγραφή (προαιρετικό)",
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
                        text = "Περιοχή (προαιρετικό)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Επιλογή Φωτογραφίας
            Text(
                text = "Φωτογραφία",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                if (photoUri != null) {
                    AsyncImage(
                        model = photoUri,
                        contentDescription = "Εικόνα Κατοικιδίου",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = "Καμία φωτογραφία επιλεγμένη",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            PrimaryButton(
                text = "Επιλογή Φωτογραφίας",
                onClick = { photoPickerLauncher.launch("image/*") }
            )

            // Submit Button
            PrimaryButton(
                text = "Δημοσίευση Αγγελίας",
                onClick = {
                    if (name.isNotBlank() && species.isNotBlank() && gender.isNotBlank() && age.isNotBlank() && photoUri != null) {
                        val newPet = Pet(
                            id = 0, // Αρχικό ID, ίσως το αναθέσει το backend
                            name = name,
                            species = species,
                            breed = breed.ifBlank { null },
                            age = age.toInt(),
                            gender = gender,
                            description = description.ifBlank { null },
                            imageUrl = photoUri.toString(),
                            uploadDate = System.currentTimeMillis(),
                            location = location.ifBlank { null }
                        )
                        println("Δημιουργήθηκε κατοικίδιο: $newPet")
                    } else {
                        println("Παρακαλώ συμπληρώστε όλα τα απαιτούμενα πεδία.")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewPostPage() {

}

