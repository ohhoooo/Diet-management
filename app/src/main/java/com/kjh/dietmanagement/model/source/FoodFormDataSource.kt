package com.kjh.dietmanagement.model.source

import com.kjh.dietmanagement.model.ApiClient
import com.kjh.dietmanagement.model.data.Food
import com.kjh.dietmanagement.model.data.ResponseFood
import retrofit2.Call

class FoodFormDataSource(private val api: ApiClient) {

    // 음식 직접 추가
    suspend fun registerFood(food: Food): Call<ResponseFood> {
        return api.registerFood(food)
    }
}