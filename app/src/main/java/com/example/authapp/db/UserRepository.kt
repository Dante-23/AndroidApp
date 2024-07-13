package com.example.authapp.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.authapp.db.models.User
import com.example.authapp.db.models.UserRequest
import com.example.authapp.db.models.UserResponse
import com.example.authapp.utils.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepository @Inject constructor(private var dao: UserDao, private var tokenManager: DummyTokenManager) {

    private val _userRepositoryLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userRepositoryLiveData : LiveData<NetworkResult<UserResponse>>
        get() = _userRepositoryLiveData
    private val TAG = "db_UserRepository"

    suspend fun createUser(user: User) {
        dao.insertUser(user)
    }

    suspend fun registerUser(userRequest: UserRequest) {
        _userRepositoryLiveData.postValue(NetworkResult.Loading())
//        Thread.sleep(3000) // Just for testing loading feature
        CoroutineScope(Dispatchers.IO).launch {
            val user = User(0, userRequest.email, userRequest.password, userRequest.username)
            val users = dao.getAllUsers()
            var userExists = false
            for (_user in users.iterator()) {
                if (user.username == _user.username || user.email == _user.email) {
                    userExists = true
                    break
                }
            }
            if (userExists) {
                _userRepositoryLiveData.postValue(NetworkResult.Error("User already exists. Please login"))
            } else {
                dao.insertUser(user)
                Log.d(TAG, "Sending success")
                _userRepositoryLiveData.postValue(
                    NetworkResult.Success(
                        UserResponse(
                            "dummy token",
                            user
                        )
                    )
                )
            }
        }
    }

    suspend fun printUsers() {
//        val users = dao.getAllUsers()
        var users : List<User> ?= null
        CoroutineScope(Dispatchers.IO).launch {
            users = dao.getAllUsers()
            for (user in users!!.iterator()) {
                Log.d(TAG, user.username)
            }
        }
    }

    fun loginUser(userRequest: UserRequest) {
        _userRepositoryLiveData.postValue(NetworkResult.Loading())
        CoroutineScope(Dispatchers.IO).launch {
            val users = dao.getAllUsers()
            Log.d(TAG, "Size: " + users.size)
            Log.d(TAG, "Input username: " + userRequest.username)
            var user: User ?= null
            var wrongPassword = false
            for (_user in users.iterator()) {
                Log.d(TAG, _user.username)
                if (_user.username == userRequest.username) {
                    user = _user
                    Log.d(TAG, "Matched with " + _user.username)
                    if (_user.password != userRequest.password) wrongPassword = true
                    break
                }
            }
            if (user == null) {
                _userRepositoryLiveData.postValue(NetworkResult.Error("User does not exists. Please register first"))
            } else if (wrongPassword) {
                _userRepositoryLiveData.postValue(NetworkResult.Error("Wrong username or password..."))
            } else {
                loginUser(user.username)
                _userRepositoryLiveData.postValue(NetworkResult.Success(UserResponse("Dummy token", user)))
            }
        }
    }

    suspend fun updateUser(user: User) {
        dao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        dao.deleteUser(user)
    }

    suspend fun getAllUsers() : List<User> {
       return dao.getAllUsers()
    }
    fun isUserLoggedIn() : Boolean {
        return tokenManager.isUserLoggedIn()
    }
    fun isInitRegisterStates() {
        Log.d(TAG, "isInitRegisterStates")
        _userRepositoryLiveData.postValue(NetworkResult.Starting())
    }
    fun loginUser(username: String) {
        Log.d(TAG, "Logging user in")
        tokenManager.loginUser(username)
    }
}