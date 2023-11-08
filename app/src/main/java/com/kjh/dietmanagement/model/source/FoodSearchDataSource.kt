package com.kjh.dietmanagement.model.source

import com.kjh.dietmanagement.model.ApiClient
import com.kjh.dietmanagement.model.data.ResponseFoods
import retrofit2.Call

class FoodSearchDataSource(private val api: ApiClient) {

    // 음식 검색
    suspend fun searchFoods(food: String): Call<ResponseFoods> {
        return api.searchFoods(food)
    }
}