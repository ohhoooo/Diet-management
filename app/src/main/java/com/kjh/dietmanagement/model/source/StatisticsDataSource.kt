package com.kjh.dietmanagement.model.source

import com.kjh.dietmanagement.model.ApiClient
import com.kjh.dietmanagement.model.data.ResponseStatistics
import retrofit2.Call

class StatisticsDataSource(private val api: ApiClient) {

    // 통계 조회
    suspend fun getStatistics(): Call<ResponseStatistics> {
        return api.getStatistics()
    }
}