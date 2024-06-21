package com.capstone.nutritrack.response

import com.google.gson.annotations.SerializedName

data class SearchFoodResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("calories") val calories: Double,
    @SerializedName("fat") val fat: Double,
    @SerializedName("carbs") val carbs: Double,
    @SerializedName("protein") val protein: Double
)