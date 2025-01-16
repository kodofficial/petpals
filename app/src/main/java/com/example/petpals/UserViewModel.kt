package com.example.petpals

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petpals.data.Pet
import com.example.petpals.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserViewModel @Inject constructor() : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    fun updateUserInfo(user: User) {
        viewModelScope.launch {
            try {
                val userRef = db.collection("users").document(user.userName)
                userRef.set(user).await()

                Log.d("UserViewModel", "User info updated successfully: ${user.userName}")
            } catch (e: Exception) {
                Log.e("UserViewModel", "Failed to update user info: ${e.message}", e)
            }
        }
    }

    private val _userPets = MutableStateFlow<List<Pet>>(emptyList())
    val userPets: StateFlow<List<Pet>> = _userPets

    fun loadUserPets(userName: String) {
        viewModelScope.launch {
            try {
                val querySnapshot = db.collection("pets")
                    .whereEqualTo("userId", userName)
                    .get()
                    .await()

                val pets = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Pet::class.java)
                }
                _userPets.value = pets
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error loading user pets: ${e.message}", e)
            }
        }
    }
}