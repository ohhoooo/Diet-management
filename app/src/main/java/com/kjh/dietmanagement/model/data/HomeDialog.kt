package com.kjh.dietmanagement.model.data

import com.google.gson.annotations.SerializedName

data class HomeDialog(
    @SerializedName("dan") val dan : Double,
    @SerializedName("food_name") val foodName : String,
    @SerializedName("ji") val ji : Double,
    @SerializedName("kcal") val kcal : Double,
    @SerializedName("meal_type") val mealType : String,
    @SerializedName("tan") val tan : Double,
    @SerializedName("image") val image : String,
)

data class ResponseHomeDialog(
    @SerializedName("message") val message : String,
    @SerializedName("success") val success : Int,
    @SerializedName("meal_data") val mealData : List<HomeDialog>
)

data class ResponseRemoveMeal(
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Int,
)