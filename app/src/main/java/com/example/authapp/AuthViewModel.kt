package com.example.authapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authapp.db.models.UserRequest
import com.example.authapp.db.UserRepository
import com.example.authapp.db.models.User
import com.example.authapp.db.models.UserResponse
import com.example.authapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userRepositoryLiveData
    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }
    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    suspend fun getAllUsers(): List<User> {
        return userRepository.getAllUsers()
    }

    fun initRegisterState() {
        userRepository.isInitRegisterStates()
    }

    fun isUserLoggedIn() : Boolean {
        return userRepository.isUserLoggedIn()
    }
    fun loginUser(username: String) {
        userRepository.loginUser(username)
    }
}