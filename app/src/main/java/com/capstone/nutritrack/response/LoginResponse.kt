package com.capstone.nutritrack.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("userId")
    val userId: String
)