package com.kjh.dietmanagement.model.source

import com.kjh.dietmanagement.model.ApiClient
import com.kjh.dietmanagement.model.data.ResponseHomeDialog
import retrofit2.Call

class HomeDialogDataSource(private val api: ApiClient) {

    // 식단 조회
    suspend fun getMeals(date: String): Call<ResponseHomeDialog> {
        return api.getMeals(date)
    }
}