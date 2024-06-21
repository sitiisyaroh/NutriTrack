package com.capstone.nutritrack.response

import com.google.gson.annotations.SerializedName

data class FoodIntakeResponse(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("foodName")
    val foodName: String,

    @field:SerializedName(" v")
    val v: Int,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("calories")
    val calories: Double,

    @field:SerializedName("userId")
    val userId: String
)
