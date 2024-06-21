package com.capstone.nutritrack.response

import com.google.gson.annotations.SerializedName

data class GetFoodIntakeResponse(

	@field:SerializedName("GetFoodIntakeResponse")
	val getFoodIntakeResponse: List<GetFoodIntakeResponseItem>
)

data class GetFoodIntakeResponseItem(

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("foodName")
	val foodName: String,

	@field:SerializedName("calories")
	val calories: Float,

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("__v")
	val v: Int
)
