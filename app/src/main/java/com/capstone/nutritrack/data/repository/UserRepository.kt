package com.capstone.nutritrack.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.nutritrack.data.ResultState
import com.capstone.nutritrack.data.api.ApiService
import com.capstone.nutritrack.data.pref.UserModel
import com.capstone.nutritrack.data.pref.UserPreference
import com.capstone.nutritrack.data.request.LoginRequest
import com.capstone.nutritrack.data.request.RegisterRequest
import com.capstone.nutritrack.data.request.SetGoalsRequest
import com.capstone.nutritrack.response.ErrorResponse
import com.capstone.nutritrack.response.GetSetGoalsResponse
import com.capstone.nutritrack.response.LoginResponse
import com.capstone.nutritrack.response.RegisterResponse
import com.capstone.nutritrack.response.SetGoalsResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.File

class UserRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    ){

    fun login(email: String,password: String): LiveData<ResultState<LoginResponse>> = liveData{
        emit(ResultState.Loading)
        try {
            Log.d(email, "emaill ni repository")
            Log.d(password, "ni password repository")
            val request = LoginRequest(email, password)
            val response = apiService.login(request)
            emit(ResultState.Success(response))
        }catch (e: HttpException){
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        }
    }

    fun signup(fullName: String, email: String, password: String): LiveData<ResultState<RegisterResponse>> = liveData{
        emit(ResultState.Loading)
        try {
            Log.d(fullName, "ni fullname repository")
            Log.d(email, "emaill ni repository")
            Log.d(password, "ni password repository")
            val request = RegisterRequest(fullName, email, password)
            val response = apiService.register(request)
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

    suspend fun setGoals(gender: String, dateOfBirth: String, height: Int, weight: Int, goalWeight: Int): SetGoalsResponse {
        val request = SetGoalsRequest(gender, dateOfBirth, height, weight, goalWeight)
        return apiService.setGoals(request)
    }

    suspend fun getGoals(userId: String): GetSetGoalsResponse {
        return apiService.getGoals(userId)
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