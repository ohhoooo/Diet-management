package com.kjh.dietmanagement.model.data

import com.google.gson.annotations.SerializedName

data class MealForm(
    @SerializedName("eat_date") val eatDate: String,
    @SerializedName("meal_type") val mealType: String,
    @SerializedName("foods") val foods: List<String>,
    @SerializedName("user_weight") val userWeight: Double,
    @SerializedName("user_height") val userHeight: Double,
    @SerializedName("image") val image: String,
)

data class ResponseMealForm(
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: String,
)