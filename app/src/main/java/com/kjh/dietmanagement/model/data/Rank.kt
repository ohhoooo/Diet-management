package com.kjh.dietmanagement.model.data

import com.google.gson.annotations.SerializedName

data class Rank(
    @SerializedName("percentage") val percentage: Double,
    @SerializedName("user_ranking") val userRanking: Int,
    @SerializedName("user_name") val userName: String,
    @SerializedName("top_ranker") val list: List<RankList>,
)

data class RankList(
    @SerializedName("balance") val balance: Double,
    @SerializedName("user_name") val userId: String
)