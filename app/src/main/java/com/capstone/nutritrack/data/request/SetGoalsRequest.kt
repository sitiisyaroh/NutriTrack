package com.capstone.nutritrack.data.request

data class SetGoalsRequest(
    val gender: String,
    val dateOfBirth: String,
    val height: Int,
    val weight: Int,
    val goalWeight: Int
)