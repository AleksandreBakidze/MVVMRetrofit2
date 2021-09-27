package com.example.apicallmvvm.data.api

import android.util.Log
import com.example.apicallmvvm.utils.StoreToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor {
    fun intercept(chain: Interceptor.Chain): Response {
        val chainBuilder = chain.request().newBuilder()
        Log.e("toke", "${StoreToken.fetchToken()}")
        StoreToken.fetchToken().let {
            chainBuilder.addHeader("Authorization", "Bearer ${StoreToken.fetchToken()}")
        }
        return chain.proceed(chainBuilder.build())
    }
}