package com.capstone.nutritrack.data.pref


data class UserModel (
    val userId: String,
    val email: String,
    val password: String,
    val isLogin: Boolean
)