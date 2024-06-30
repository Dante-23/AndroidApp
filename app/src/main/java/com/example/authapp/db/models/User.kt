package com.example.authapp.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val email: String,
    val password: String,
    val username: String
)
