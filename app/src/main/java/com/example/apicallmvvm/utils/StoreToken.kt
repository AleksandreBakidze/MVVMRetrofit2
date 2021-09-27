package com.example.apicallmvvm.utils

import android.content.Context
import android.content.SharedPreferences

object StoreToken {

    private var sharedPreferences : SharedPreferences? = null

    fun initialize(context: Context, sharedPreferences: SharedPreferences) {
        StoreToken.sharedPreferences = sharedPreferences
    }

    fun saveToken(token: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(Constants.SHOP_ACCESS, token)
        editor?.apply()
    }

    fun fetchToken(): String? {
        return sharedPreferences?.getString(Constants.SHOP_ACCESS, null)
    }

}