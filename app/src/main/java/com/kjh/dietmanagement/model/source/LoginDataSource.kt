package com.kjh.dietmanagement.model.source

import com.kjh.dietmanagement.model.ApiClient
import com.kjh.dietmanagement.model.data.Login
import com.kjh.dietmanagement.model.data.ResponseLogin
import retrofit2.Call

class LoginDataSource(private val api: ApiClient) {

    // 로그인
    suspend fun loginUser(login: Login): Call<ResponseLogin> {
        return api.loginUser(login)
    }
}