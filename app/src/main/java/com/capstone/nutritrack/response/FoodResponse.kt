package com.capstone.nutritrack.response

import com.google.gson.annotations.SerializedName

data class FoodResponse(

    @field:SerializedName("FoodResponse")
    val foodResponse: List<FoodResponseItem>
)

data class FoodResponseItem(

    @field:SerializedName("carbs")
    val carbs: Any,

    @field:SerializedName("protein")
    val protein: Any,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("fat")
    val fat: Any,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("calories")
    val calories: Int
)
