package com.example.petpals

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {
    //Initialize an instance of Firebase Authentication
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    //Initialize an instance of Firestore
    private val db = FirebaseFirestore.getInstance()


    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {

        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email : String, password : String) {

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password are empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signup(email : String, password : String, firstName : String, lastName : String, userName : String) {

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password are empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated

                    val user = auth.currentUser
                    val userId = user?.uid
                    val userData = hashMapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "userName" to userName
                    )

                    if (userId != null) {

                        db.collection("users")
                            .document(userId)
                            .set(userData)
                            .addOnSuccessListener {
                                Log.d(TAG, "User with ID: $userId added")
                            }
                            .addOnFailureListener{ e ->
                                Log.w(TAG,"Error adding document", e)
                            }
                    }
                } else {
                    _authState.value = AuthState.Unauthenticated
                }
            }
    }



}

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}