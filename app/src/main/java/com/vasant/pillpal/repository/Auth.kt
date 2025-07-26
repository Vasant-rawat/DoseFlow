package com.vasant.pillpal.repository

import com.vasant.pillpal.utils.AuthResult

interface Auth {
    suspend fun SingIn(email: String, password: String): AuthResult
    suspend fun SingUp(email: String, password: String): AuthResult

}