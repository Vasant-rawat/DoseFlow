package com.vasant.pillpal.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasant.pillpal.repository.Auth
import com.vasant.pillpal.utils.AuthResult
import com.vasant.pillpal.utils.FirebaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(
    private val auth: Auth
) : ViewModel() {
    var firebaseState by mutableStateOf<FirebaseState>(FirebaseState.Loading)
    var authState by mutableStateOf<AuthResult>(AuthResult.Loading)
    fun login(email: String, password: String) {
        viewModelScope.launch {
            firebaseState = FirebaseState.Loading
            authState = auth.SingIn(email, password)
            firebaseState = FirebaseState.Done
        }
    }

    fun singUp(email: String, password: String) {
        viewModelScope.launch {
            firebaseState = FirebaseState.Loading
            authState = auth.SingUp(email, password)
            firebaseState = FirebaseState.Done
        }
    }
}