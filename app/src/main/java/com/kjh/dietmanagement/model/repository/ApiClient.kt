package com.kjh.dietmanagement.model.repository

import com.kjh.dietmanagement.model.data.Join
import com.kjh.dietmanagement.model.data.Login
import com.kjh.dietmanagement.model.data.ResponseJoin
import com.kjh.dietmanagement.model.data.ResponseLogin
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiClient {
    // 회원 가입
    @POST("/user/signup")
    fun joinUser(@Body join: Join): Call<ResponseJoin>

    // 로그인
    @POST("/user/login")
    fun loginUser(@Body login: Login): Call<ResponseLogin>

    // base Url
    companion object {

        private const val baseUrl = "http://13.53.255.104:8000/"

        fun create(): ApiClient {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient::class.java)
        }
    }
}