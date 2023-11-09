package com.kjh.dietmanagement.model.repository

import com.kjh.dietmanagement.model.data.ResponseHomeDialog
import com.kjh.dietmanagement.model.source.HomeDialogDataSource
import retrofit2.Call
import retrofit2.Response

class HomeDialogRepository(private val homeDialogDataSource: HomeDialogDataSource) {

    // 식단 조회
    suspend fun getMeals(date: String, callback: (ResponseHomeDialog?, String) -> Unit) {
        homeDialogDataSource.getMeals(date).enqueue(object : retrofit2.Callback<ResponseHomeDialog> {
            override fun onResponse(call: Call<ResponseHomeDialog>, response: Response<ResponseHomeDialog>) {
                if (response.isSuccessful) {
                    callback(response.body(), "응답 성공")
                } else {
                    callback(response.body(), "응답 실패")
                }
            }

            override fun onFailure(call: Call<ResponseHomeDialog>, t: Throwable) {
                callback(null, "연결 실패")
            }
        })
    }
}