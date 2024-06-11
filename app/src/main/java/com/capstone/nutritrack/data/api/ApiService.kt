package com.capstone.nutritrack.data.api

import com.capstone.nutritrack.data.request.RegisterRequest
import com.capstone.nutritrack.response.LoginResponse
import com.capstone.nutritrack.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService{

    @POST("signup")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

}