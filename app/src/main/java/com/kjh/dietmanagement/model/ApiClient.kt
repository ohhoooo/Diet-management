package com.kjh.dietmanagement.model

import com.kjh.dietmanagement.model.data.Food
import com.kjh.dietmanagement.model.data.Join
import com.kjh.dietmanagement.model.data.Login
import com.kjh.dietmanagement.model.data.Rank
import com.kjh.dietmanagement.model.data.ResponseFood
import com.kjh.dietmanagement.model.data.ResponseJoin
import com.kjh.dietmanagement.model.data.ResponseLogin
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiClient {
    // 회원 가입
    @POST("/user/signup")
    fun joinUser(@Body join: Join): Call<ResponseJoin>

    // 로그인
    @POST("/user/login")
    fun loginUser(@Body login: Login): Call<ResponseLogin>

    // 음식 직접 추가
    @POST("/food/add")
    fun registerFood(@Body food: Food): Call<ResponseFood>

    // 랭킹 조회
    @GET("/user/ranking/day")
    fun checkRanking(): Call<Rank>

    // base Url
    companion object {

        private const val baseUrl = "http://13.53.255.104:8000/"

        private val client = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor())
            .build()

        fun create(): ApiClient {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient::class.java)
        }
    }
}