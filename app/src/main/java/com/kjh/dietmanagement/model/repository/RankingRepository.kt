package com.kjh.dietmanagement.model.repository

import com.kjh.dietmanagement.model.data.Rank
import com.kjh.dietmanagement.model.source.RankingDataSource
import retrofit2.Call
import retrofit2.Response

class RankingRepository(private val rankingDataSource: RankingDataSource) {

    // 랭킹 조회
    suspend fun checkRanking(callback: (Rank?, String) -> Unit) {
        rankingDataSource.checkRanking().enqueue(object : retrofit2.Callback<Rank> {
            override fun onResponse(call: Call<Rank>, response: Response<Rank>) {
                if (response.isSuccessful) {
                    callback(response.body(), "응답 성공")
                } else {
                    callback(response.body(), "응답 실패")
                }
            }

            override fun onFailure(call: Call<Rank>, t: Throwable) {
                callback(null, "연결 실패")
            }
        })
    }
}