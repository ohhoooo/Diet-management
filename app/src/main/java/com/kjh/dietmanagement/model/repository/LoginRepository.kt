package com.kjh.dietmanagement.model.repository

import com.kjh.dietmanagement.model.data.Login
import com.kjh.dietmanagement.model.data.ResponseLogin
import com.kjh.dietmanagement.model.source.LoginDataSource
import retrofit2.Call
import retrofit2.Response

class LoginRepository(private val loginDataSource: LoginDataSource) {

    // 로그인
    suspend fun loginUser(login: Login, callback: (String, String?) -> Unit) {
        loginDataSource.loginUser(login).enqueue(object : retrofit2.Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    val responseBody = response.body().toString()
                    callback("로그인 성공", responseBody.substring(79,responseBody.length-2))
                } else {
                    callback("로그인 실패", null)
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                callback("로그인 실패", null)
            }
        })
    }
}