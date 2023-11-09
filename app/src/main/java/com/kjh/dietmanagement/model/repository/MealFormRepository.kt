package com.kjh.dietmanagement.model.repository

import com.kjh.dietmanagement.model.data.MealForm
import com.kjh.dietmanagement.model.data.ResponseMealForm
import com.kjh.dietmanagement.model.source.MealFormDataSource
import retrofit2.Call
import retrofit2.Response

class MealFormRepository(private val mealFormDataSource: MealFormDataSource) {

    // 식단 저장
    suspend fun saveMeal(meal: MealForm, callback: (ResponseMealForm?, String) -> Unit) {
        mealFormDataSource.saveMeal(meal).enqueue(object : retrofit2.Callback<ResponseMealForm> {
            override fun onResponse(call: Call<ResponseMealForm>, response: Response<ResponseMealForm>) {
                if (response.isSuccessful) {
                    callback(response.body(), "응답 성공")
                } else {
                    callback(response.body(), "응답 실패")
                }
            }

            override fun onFailure(call: Call<ResponseMealForm>, t: Throwable) {
                callback(null, "연결 실패")
            }
        })
    }
}