package com.example.apicallmvvm.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getShops() = apiService.getShops()

}