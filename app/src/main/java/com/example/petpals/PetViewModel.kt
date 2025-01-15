package com.example.petpals

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petpals.data.Pet
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class PetViewModel @Inject constructor() : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

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
                    pet.age?.toString()?.contains(query) == true ||
                    pet.description?.contains(query, ignoreCase = true) == true

            val matchesCategory = category == null || pet.species.equals(category, ignoreCase = true)

            matchesSearchQuery && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        loadPets()
    }

    private fun loadPets() {
        viewModelScope.launch {
            db.collection("pets")
                .get()
                .addOnSuccessListener { result ->
                    val petsList = result.documents.mapNotNull { document ->
                        document.toObject(Pet::class.java)
                    }
                    _pets.value = petsList
                }
                .addOnFailureListener {e ->
                    Log.e("PetViewModel", "Failed to load pets: ${e.message}", e)
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSelectedCategory(category: String?) {
        _selectedCategory.value = category
    }
}
