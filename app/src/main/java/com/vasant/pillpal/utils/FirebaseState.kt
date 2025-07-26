package com.vasant.pillpal.utils

import com.google.firebase.auth.FirebaseUser

sealed class FirebaseState {
    object IsIdle : FirebaseState()
    object Loading : FirebaseState()
    object Done : FirebaseState()
}

sealed class AuthResult {
    data class Success(val user: FirebaseUser?) : AuthResult()
    data class Error(val message: String) : AuthResult()
    object Loading : AuthResult()
}