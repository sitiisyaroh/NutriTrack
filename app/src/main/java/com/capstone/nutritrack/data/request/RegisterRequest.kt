package com.capstone.nutritrack.data.request

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)