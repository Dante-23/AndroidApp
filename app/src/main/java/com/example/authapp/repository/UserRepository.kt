package com.example.authapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.authapp.api.UserApi
import com.example.authapp.models.UserRequest
import com.example.authapp.models.UserResponse
import com.example.authapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {
    private val TAG: String = "UserRepository"
    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData
    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.signup(userRequest)
        Log.d(TAG, response.body().toString())
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            // Retrofit auto parses response JSON on success
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            // Retrofit does not parse on error
            val error = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue((NetworkResult.Error(error.getString("message"))))
        } else {
            _userResponseLiveData.postValue((NetworkResult.Error("Something went wrong")))
        }
    }

    suspend fun loginUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.signin(userRequest)
        Log.d(TAG, response.body().toString())
        handleResponse(response)
    }
}