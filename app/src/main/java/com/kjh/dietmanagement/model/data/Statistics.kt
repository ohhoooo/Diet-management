package com.kjh.dietmanagement.model.data

import com.google.gson.annotations.SerializedName

data class Statistics(
    @SerializedName("data") val data: Double,
    @SerializedName("date") val date: String,
)

data class ResponseStatistics(
    @SerializedName("isSuccess") val isSuccess: String,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: StatisticsNutrientType,
)

data class StatisticsNutrientType(
    @SerializedName("dan") val dan: StatisticsDateType,
    @SerializedName("ji") val ji: StatisticsDateType,
    @SerializedName("kcal") val kcal: StatisticsDateType,
    @SerializedName("tan") val tan: StatisticsDateType,
    @SerializedName("weight") val weight: StatisticsDateType,
)

data class StatisticsDateType(
    @SerializedName("daily") val daily: List<Statistics>,
    @SerializedName("weekly") val weekly: List<Statistics>,
    @SerializedName("monthly") val monthly: List<Statistics>,
    @SerializedName("recommend") val recommend: Int,
    @SerializedName("y-axis") val yaxis: Int,
)