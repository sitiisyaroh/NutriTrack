package com.capstone.nutritrack.data.api

import com.capstone.nutritrack.data.request.AddFoodIntakeRequest
import com.capstone.nutritrack.data.request.LoginRequest
import com.capstone.nutritrack.data.request.RegisterRequest
import com.capstone.nutritrack.data.request.SetGoalsRequest
import com.capstone.nutritrack.response.FoodIntakeResponse
import com.capstone.nutritrack.response.GetFoodIntakeResponse
import com.capstone.nutritrack.response.GetFoodIntakeResponseItem
import com.capstone.nutritrack.response.GetSetGoalsResponse
import com.capstone.nutritrack.response.LoginResponse
import com.capstone.nutritrack.response.RegisterResponse
import com.capstone.nutritrack.response.SearchFoodResponse
import com.capstone.nutritrack.response.SetGoalsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{

    @POST("signup")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("setGoals")
    suspend fun setGoals(
        @Body request: SetGoalsRequest
    ): SetGoalsResponse

    @GET("setGoals/{userId}")
    suspend fun getGoals(
        @Path("userId") userId: String
    ): List<GetSetGoalsResponse>

    @GET("search_food")
    suspend fun searchFood(
        @Query("q") query: String
    ): Response<List<SearchFoodResponse>>

    @POST("foodIntake")
    suspend fun addFoodIntake(
        @Body request:AddFoodIntakeRequest
    ): Response<FoodIntakeResponse>

    @GET("foodIntake/{userId}")
    suspend fun getFoodIntakeByUserId(
        @Path("userId") userId: String
    ): Response<List<GetFoodIntakeResponseItem>>
}

