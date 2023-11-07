package com.kjh.dietmanagement.model.data

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("user_id") val userId: String,
    @SerializedName("password") val password : String
)

data class ResponseLogin (
    @SerializedName("code") val code: Int,
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
)

data class Result (
    @SerializedName("jwt") val jwt : String
)