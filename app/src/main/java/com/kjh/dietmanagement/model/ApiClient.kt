package com.kjh.dietmanagement.model

import com.kjh.dietmanagement.model.data.Food
import com.kjh.dietmanagement.model.data.Join
import com.kjh.dietmanagement.model.data.Login
import com.kjh.dietmanagement.model.data.MealForm
import com.kjh.dietmanagement.model.data.Rank
import com.kjh.dietmanagement.model.data.ResponseFood
import com.kjh.dietmanagement.model.data.ResponseFoods
import com.kjh.dietmanagement.model.data.ResponseHomeDialog
import com.kjh.dietmanagement.model.data.ResponseJoin
import com.kjh.dietmanagement.model.data.ResponseLogin
import com.kjh.dietmanagement.model.data.ResponseMealForm
import com.kjh.dietmanagement.model.data.ResponseRemoveMeal
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {
    // 회원 가입
    @POST("/user/signup")
    fun joinUser(@Body join: Join): Call<ResponseJoin>

    // 로그인
    @POST("/user/login")
    fun loginUser(@Body login: Login): Call<ResponseLogin>

    // 식단 조회
    @GET("/get/meals")
    fun getMeals(@Query("eat_date") date: String): Call<ResponseHomeDialog>

    // 식단 삭제
    @DELETE("/user/deletemeals")
    fun removeMeal(@Query("eat_date") date: String,
                   @Query("meal_type") type: String,
                   @Query("food_name") name: String): Call<ResponseRemoveMeal>

    // 식단 저장
    @POST("/user/addmeal")
    fun saveMeal(@Body meal: MealForm): Call<ResponseMealForm>

    // 음식 직접 추가
    @POST("/food/add")
    fun registerFood(@Body food: Food): Call<ResponseFood>

    // 음식 검색
    @GET("/food/search")
    fun searchFoods(@Query("q") q: String): Call<ResponseFoods>

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