package com.capstone.nutritrack.response

import com.google.gson.annotations.SerializedName

data class SetGoalsResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("dateOfBirth")
    val dateOfBirth: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("weight")
    val weight: Int,

    @SerializedName("goalWeight")
    val goalWeight: Int,

    @SerializedName("bmi")
    val bmi: Float,

    @SerializedName("bmiCategory")
    val bmiCategory: String,

    @SerializedName("dailyCalorieNeeds")
    val dailyCalorieNeeds: Float,

    @SerializedName("_id")
    val id: String,

    @SerializedName("__v")
    val version: Int
)
