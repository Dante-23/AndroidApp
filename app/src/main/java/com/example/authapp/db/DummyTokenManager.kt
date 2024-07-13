package com.example.authapp.db

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DummyTokenManager @Inject constructor(@ApplicationContext context: Context) {
    var username: String?=null
    var token: String?=null
    fun getRandomString(length: Int) : String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
    fun isUserLoggedIn() : Boolean {
        return username != null
    }
    fun loginUser(uname: String) {
        username = uname
        token = getRandomString(32)
    }
}