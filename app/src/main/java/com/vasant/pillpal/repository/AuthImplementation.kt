package com.vasant.pillpal.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.vasant.pillpal.utils.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "Firebase"

class AuthImplementation @Inject constructor(
    private val _auth: FirebaseAuth
) : Auth {
    override suspend fun SingIn(email: String, password: String): AuthResult {
        return try {
            val result = _auth.signInWithEmailAndPassword(email, password).await()
            Log.d(TAG, "User successfully Login in.")
            AuthResult.Success(user = result.user)
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun SingUp(email: String, password: String): AuthResult {
        return try {
            val result = _auth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user)
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
        }
    }
}