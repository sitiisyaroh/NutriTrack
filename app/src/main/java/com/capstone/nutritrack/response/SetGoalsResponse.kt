package com.capstone.nutritrack.response

import com.google.gson.annotations.SerializedName

data class SetGoalsResponse (
    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String,

    @field:SerializedName("height")
    val height: Int,

    @field:SerializedName("weight")
    val weight: Int,

    @field:SerializedName("goalWeight")
    val goalWeight: Int,

    @field:SerializedName("bmi")
    val bmi : Float,

    @field:SerializedName("bmiCategory")
    val bmiCategory : String,

    @field:SerializedName("dailyCalorieNeeds")
    val dailyCalorieNeeds: Float,

    @field:SerializedName("_id")
    val _id: String,

    @field:SerializedName("__v")
    val __v: Int
)