package com.capstone.nutritrack.data.request

import com.google.gson.annotations.SerializedName

data class SetGoalsRequest(
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
    val goalWeight: Int
)