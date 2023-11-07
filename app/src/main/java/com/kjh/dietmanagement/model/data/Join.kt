package com.kjh.dietmanagement.model.data

import com.google.gson.annotations.SerializedName

data class Join(
    @SerializedName("user_id") val userId: String,
    @SerializedName("user_name") val userName: String,
    @SerializedName("password") val password: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("weight") val weight: Double,
    @SerializedName("height") val height: Double,
    @SerializedName("goal_w") val goalW: Double
)

data class ResponseJoin (
    @SerializedName("code") val code: Int,
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("message") val message: String
)