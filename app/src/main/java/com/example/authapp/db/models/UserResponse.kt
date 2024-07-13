package com.example.authapp.db.models

data class UserResponse(
    val token: String,
    val user: User
)