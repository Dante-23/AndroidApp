package com.example.authapp.repository

import android.util.Log
import com.example.authapp.api.UserApi
import com.example.authapp.models.UserRequest
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {
    private val TAG: String = "UserRepository"
    suspend fun registerUser(userRequest: UserRequest) {
        val response = userApi.signup(userRequest)
        Log.d(TAG, response.body().toString())
    }
    suspend fun loginUser(userRequest: UserRequest) {
        val response = userApi.signin(userRequest)
        Log.d(TAG, response.body().toString())
    }
}