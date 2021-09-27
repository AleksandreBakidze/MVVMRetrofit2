package com.example.apicallmvvm.data.repository

import com.example.apicallmvvm.data.api.ApiHelper
import com.example.apicallmvvm.data.model.Shop
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {
    fun getShops(): Single<List<Shop>> {
        return apiHelper.getShops()
    }
}