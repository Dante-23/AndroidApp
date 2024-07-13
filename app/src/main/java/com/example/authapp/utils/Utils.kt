package com.example.authapp.utils

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

class Utils {
    companion object {
        fun validateCredentials(username: String, emailAddr: String, password: String) : Pair<Boolean, String> {
            var result = Pair(true, "")
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(emailAddr) || TextUtils.isEmpty(password)) {
                result = Pair(false, "None of the fields can be empty")
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddr).matches()) {
                result = Pair(false, "Invalid email address")
            }
            return result
        }
    }
}