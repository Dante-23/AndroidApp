package com.example.authapp.db

import com.example.authapp.db.models.User
import com.example.authapp.models.UserRequest
import javax.inject.Inject

class UserRepository @Inject constructor(private var dao: UserDao) {

    suspend fun createUser(user: User) {
        dao.insertUser(user)
    }

    suspend fun registerUser(userRequest: UserRequest) {
        val user = User(0, userRequest.email, userRequest.password, userRequest.username)
        dao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        dao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        dao.deleteUser(user)
    }

    fun getAllUsers() : List<User> {
       return dao.getAllUsers()
    }
}