package com.example.apicallmvvm.data.api

import com.example.apicallmvvm.data.model.Shop
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImpl : ApiService {
    override fun getShops(): Single<List<Shop>> {
        return Rx2AndroidNetworking.get("https://moitane-api.lemon.do/v1/Shops")
            .build()
            .getObjectListSingle(Shop::class.java)
    }
}