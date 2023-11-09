package com.kjh.dietmanagement.model.repository

import com.kjh.dietmanagement.model.data.ResponseStatistics
import com.kjh.dietmanagement.model.source.StatisticsDataSource
import retrofit2.Call
import retrofit2.Response

class StatisticsRepository(private val statisticsDataSource: StatisticsDataSource) {

    // 통계 조회
    suspend fun getStatistics(callback: (ResponseStatistics?, String) -> Unit) {
        statisticsDataSource.getStatistics().enqueue(object : retrofit2.Callback<ResponseStatistics> {
            override fun onResponse(call: Call<ResponseStatistics>, response: Response<ResponseStatistics>) {
                if (response.isSuccessful) {
                    callback(response.body(), "응답 성공")
                } else {
                    callback(response.body(), "응답 실패")
                }
            }

            override fun onFailure(call: Call<ResponseStatistics>, t: Throwable) {
                callback(null, "연결 실패")
            }
        })
    }
}