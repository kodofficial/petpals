package com.example.petpals.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.petpals.R
import com.example.petpals.data.Pet

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