package com.kjh.dietmanagement.model

import com.kjh.dietmanagement.view.common.Application
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${Application.preferences.getString("token")}")
            .build()

        return chain.proceed(request)
    }
}