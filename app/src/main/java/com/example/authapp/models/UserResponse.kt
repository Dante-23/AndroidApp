package com.example.authapp.models

data class UserResponse(
    val token: String,
    val user: User
)