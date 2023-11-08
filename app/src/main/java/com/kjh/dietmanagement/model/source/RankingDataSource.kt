package com.kjh.dietmanagement.model.source

import com.kjh.dietmanagement.model.ApiClient
import com.kjh.dietmanagement.model.data.Rank
import retrofit2.Call

class RankingDataSource(private val api: ApiClient) {

    // 랭킹 조회
    suspend fun checkRanking(): Call<Rank> {
        return api.checkRanking()
    }
}