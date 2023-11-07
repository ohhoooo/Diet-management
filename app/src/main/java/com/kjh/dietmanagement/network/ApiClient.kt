package com.kjh.dietmanagement.network

import com.kjh.dietmanagement.model.Join
import com.kjh.dietmanagement.model.Login
import com.kjh.dietmanagement.model.ResponseJoin
import com.kjh.dietmanagement.model.ResponseLogin
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