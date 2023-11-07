package com.kjh.dietmanagement.model.source

import com.kjh.dietmanagement.model.ApiClient
import com.kjh.dietmanagement.model.data.Join
import com.kjh.dietmanagement.model.data.ResponseJoin
import retrofit2.Call

class JoinDataSource(private val api: ApiClient) {

    // 회원 가입
    suspend fun joinUser(join: Join): Call<ResponseJoin> {
        return api.joinUser(join)
    }
}