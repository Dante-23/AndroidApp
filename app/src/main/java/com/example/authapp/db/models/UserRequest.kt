package com.example.authapp.db.models

data class UserRequest(
    val email: String,
    val password: String,
    val username: String
)