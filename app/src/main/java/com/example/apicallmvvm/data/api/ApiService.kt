package com.example.apicallmvvm.data.api

import com.example.apicallmvvm.data.model.Shop
import io.reactivex.Single

interface ApiService {

    fun getShops(): Single<List<Shop>>

}