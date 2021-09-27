package com.example.apicallmvvm.data.app

import android.app.Application
import android.content.Context
import com.example.apicallmvvm.utils.StoreToken

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        StoreToken.initialize(this,getSharedPreferences("_pl_",Context.MODE_PRIVATE))
    }
}