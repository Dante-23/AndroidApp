package com.example.authapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authapp.models.UserRequest
import com.example.authapp.db.UserRepository
import com.example.authapp.db.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }
    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
//            userRepository.loginUser(userRequest)
        }
    }

    fun getAllUsers(): List<User> {
        return userRepository.getAllUsers()
    }
}