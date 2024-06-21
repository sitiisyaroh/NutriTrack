package com.capstone.nutritrack.data.request

data class AddFoodIntakeRequest(
    val userId: String,
    val foodName: String,
    val calories: Double
)


