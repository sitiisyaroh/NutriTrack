package com.capstone.nutritrack.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.nutritrack.data.ResultState
import com.capstone.nutritrack.data.api.ApiService
import com.capstone.nutritrack.data.pref.UserModel
import com.capstone.nutritrack.data.pref.UserPreference
import com.capstone.nutritrack.response.ErrorResponse
import com.capstone.nutritrack.response.LogInResponse
import com.capstone.nutritrack.response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.File

class UserRepository(

    private val apiService: ApiService,
    private val userPreference: UserPreference,

    ){


    fun login(email: String,password: String): LiveData<ResultState<LogInResponse>> = liveData{
        emit(ResultState.Loading)
        try {
            val response = apiService.login(email,password)
            emit(ResultState.Success(response))
        }catch (e: HttpException){
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        }
    }

    fun signup(name: String, email: String, password: String): LiveData<ResultState<RegisterResponse>> = liveData{
        emit(ResultState.Loading)
        try {
            val response = apiService.register(name,email,password)
            emit(ResultState.Success(response))
        }catch (e: HttpException){
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        }
    }

    suspend fun saveSession(user: UserModel){
        userPreference.saveSession(user)
    }
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }
    suspend fun logout(){
        userPreference.logout()
    }

    companion object{

        private var INSTANCE: UserRepository? = null

        fun clearInstance(){
            INSTANCE = null
        }

        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreference
        ): UserRepository =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: UserRepository(apiService,userPreferences)
            }.also { INSTANCE = it }
    }
}