package com.kjh.dietmanagement.model.repository

import com.kjh.dietmanagement.model.data.ResponseFoods
import com.kjh.dietmanagement.model.source.FoodSearchDataSource
import retrofit2.Call
import retrofit2.Response

class FoodSearchRepository(private val foodSearchDataSource: FoodSearchDataSource) {

    // 음식 검색
    suspend fun searchFoods(food: String, callback: (ResponseFoods?, String) -> Unit) {
        foodSearchDataSource.searchFoods(food).enqueue(object : retrofit2.Callback<ResponseFoods> {
            override fun onResponse(call: Call<ResponseFoods>, response: Response<ResponseFoods>) {
                if (response.isSuccessful) {
                    callback(response.body(), "응답 성공")
                } else {
                    callback(response.body(), "응답 실패")
                }
            }

            override fun onFailure(call: Call<ResponseFoods>, t: Throwable) {
                callback(null, "연결 실패")
            }
        })
    }
}