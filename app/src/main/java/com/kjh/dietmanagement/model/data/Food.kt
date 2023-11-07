package com.kjh.dietmanagement.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    @SerializedName("food_name") val name: String, // 음식 이름
    @SerializedName("kcal") val calorie: Double, // 칼로리
    @SerializedName("tan") val carbohydrate: Double, // 탄수화물
    @SerializedName("dan") val protein: Double, // 단백질
    @SerializedName("ji") val fat: Double, // 지방
) : Parcelable

data class ResponseFood(
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: String,
)