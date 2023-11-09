package com.kjh.dietmanagement.model.source

import com.kjh.dietmanagement.model.ApiClient
import com.kjh.dietmanagement.model.data.MealForm
import com.kjh.dietmanagement.model.data.ResponseMealForm
import retrofit2.Call

class MealFormDataSource(private val api: ApiClient) {

    // 식단 저장
    suspend fun saveMeal(meal: MealForm): Call<ResponseMealForm> {
        return api.saveMeal(meal)
    }
}