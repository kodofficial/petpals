package com.example.petpals

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.net.Uri
import com.example.petpals.data.Pet
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class PetViewModel @Inject constructor() : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    val filteredPets = combine(_pets, _searchQuery, _selectedCategory) { pets, query, category ->
        pets.filter { pet ->
            val matchesSearchQuery = query.isEmpty() ||
                    pet.name.contains(query, ignoreCase = true) ||
                    pet.species.contains(query, ignoreCase = true) ||
                    pet.breed?.contains(query, ignoreCase = true) == true ||
                    pet.age.toString().contains(query) == true ||
                    pet.description?.contains(query, ignoreCase = true) == true

            val matchesCategory = category == null || pet.species.equals(category, ignoreCase = true)

            matchesSearchQuery && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        loadPets()
        loadLatestPets()
    }

    private fun loadPets() {
        viewModelScope.launch {
            db.collection("pets")
                .get()
                .addOnSuccessListener { result ->
                    val petsList = result.documents.mapNotNull { document ->
                        val pet = document.toObject(Pet::class.java)
                        pet?.copy(imageUrl = document.getString("imageUrl") ?: pet.imageUrl)
                    }
                    _pets.value = petsList
                }
                .addOnFailureListener { e ->
                    Log.e("PetViewModel", "Failed to load pets: ${e.message}", e)
                }
        }
    }

    private val _latestPets = MutableStateFlow<List<Pet>>(emptyList())
    val latestPets: StateFlow<List<Pet>> = _latestPets

    private fun loadLatestPets() {
        viewModelScope.launch {
            db.collection("pets")
                .orderBy("uploadDate", Query.Direction.DESCENDING)
                .limit(4)
                .get()
                .addOnSuccessListener { result ->
                    val latestPetsList = result.documents.mapNotNull { document ->
                        document.toObject(Pet::class.java)
                    }
                    _latestPets.value = latestPetsList
                    Log.d("PetViewModel", "Loaded latest 4 pets: $latestPetsList") // Debugging
                }
                .addOnFailureListener { e ->
                    Log.e("PetViewModel", "Failed to load latest pets: ${e.message}", e)
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSelectedCategory(category: String?) {
        _selectedCategory.value = category
    }

    suspend fun uploadImageAndGetUrl(uri: Uri): String? {
        return try {
            val fileRef = storage.reference.child("images/pets/${System.currentTimeMillis()}")
            val uploadTask = fileRef.putFile(uri).await()
            uploadTask.storage.downloadUrl.await().toString()
        } catch (e: Exception) {
            Log.e("PetViewModel", "Failed to upload image: ${e.message}", e)
            null
        }
    }

    fun addPet(pet: Pet, imageUri: Uri) = viewModelScope.launch {
        val imageUrl = uploadImageAndGetUrl(imageUri) ?: return@launch
        val petWithImageUrl = pet.copy(imageUrl = imageUrl)
        db.collection("pets").add(petWithImageUrl)
            .addOnSuccessListener { documentReference ->
                Log.d("PetViewModel", "Pet added with ID: ${documentReference.id}")
                _pets.value = _pets.value + petWithImageUrl
            }
            .addOnFailureListener { e ->
                Log.e("PetViewModel", "Error adding pet: ${e.message}", e)
            }
    }

//    fun editPet(pet: Pet) {
//        viewModelScope.launch {
//            try {
//                val petRef = db.collection("pets").document(pet.id.toString())
//                petRef.set(pet).await()
//
//                // Update the local list
//                _pets.value = _pets.value.map {
//                    if (it.id == pet.id) pet else it
//                }
//
//                Log.d("PetViewModel", "Pet updated successfully: ${pet.name}")
//            } catch (e: Exception) {
//                Log.e("PetViewModel", "Failed to update pet: ${e.message}", e)
//            }
//        }
//    }
//
//    fun deletePet(pet: Pet) {
//        viewModelScope.launch {
//            try {
//                val petRef = db.collection("pets").document(pet.id.toString())
//                petRef.delete().await()
//
//                // Update the local list by removing the deleted pet
//                _pets.value = _pets.value.filter { it.id != pet.id }
//
//                Log.d("PetViewModel", "Pet deleted successfully: ${pet.name}")
//            } catch (e: Exception) {
//                Log.e("PetViewModel", "Failed to delete pet: ${e.message}", e)
//            }
//        }
//    }
}
