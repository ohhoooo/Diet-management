package com.kjh.dietmanagement.model.repository

import com.kjh.dietmanagement.model.data.Join
import com.kjh.dietmanagement.model.data.ResponseJoin
import com.kjh.dietmanagement.model.source.JoinDataSource
import retrofit2.Call
import retrofit2.Response

class JoinRepository(private val joinDataSource: JoinDataSource) {

    // 회원 가입
    suspend fun joinUser(join: Join, callback: (String) -> Unit) {
        joinDataSource.joinUser(join).enqueue(object : retrofit2.Callback<ResponseJoin> {
            override fun onResponse(call: Call<ResponseJoin>, response: Response<ResponseJoin>) {
                if (response.isSuccessful) {
                    callback("응답 성공")
                } else {
                    callback("응답 실패")
                }
            }

            override fun onFailure(call: Call<ResponseJoin>, t: Throwable) {
                callback("통신 실패")
            }
        })
    }
}