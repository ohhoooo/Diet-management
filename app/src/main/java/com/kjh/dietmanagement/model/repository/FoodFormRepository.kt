package com.kjh.dietmanagement.model.repository

import com.kjh.dietmanagement.model.data.Food
import com.kjh.dietmanagement.model.data.ResponseFood
import com.kjh.dietmanagement.model.source.FoodFormDataSource
import retrofit2.Call
import retrofit2.Response

class FoodFormRepository(private val foodFormDataSource: FoodFormDataSource) {

    // 음식 직접 추가
    suspend fun registerFood(food: Food, callback: (String) -> Unit) {
        foodFormDataSource.registerFood(food).enqueue(object : retrofit2.Callback<ResponseFood> {
            override fun onResponse(call: Call<ResponseFood>, response: Response<ResponseFood>) {
                if (response.isSuccessful) {
                    callback("음식 추가 성공")
                } else {
                    callback("음식 추가 실패")
                }
            }

            override fun onFailure(call: Call<ResponseFood>, t: Throwable) {
                callback("음식 추가 실패")
            }
        })
    }
}