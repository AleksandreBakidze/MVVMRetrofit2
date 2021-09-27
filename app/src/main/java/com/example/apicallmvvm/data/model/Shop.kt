package com.example.apicallmvvm.data.model

import com.google.gson.annotations.SerializedName

data class Shop(
    @SerializedName("address")
    val address: String,
    @SerializedName("administratorsIds")
    val administratorsIds: Any,
    @SerializedName("averageRating")
    val averageRating: Double,
    @SerializedName("backgroundImageThumbUrl")
    val backgroundImageThumbUrl: Any,
    @SerializedName("backgroundUrl")
    val backgroundUrl: String,
    @SerializedName("categoriesCount")
    val categoriesCount: Int,
    @SerializedName("channel")
    val channel: Any,
    @SerializedName("deliveryFee")
    val deliveryFee: Double,
    @SerializedName("deliveryFeeType")
    val deliveryFeeType: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("distance")
    val distance: String,
    @SerializedName("estimatedTime")
    val estimatedTime: String,
    @SerializedName("expressProductsLimit")
    val expressProductsLimit: Int,
    @SerializedName("expressRadius")
    val expressRadius: Any,
    @SerializedName("hasExpressDelivery")
    val hasExpressDelivery: Boolean,
    @SerializedName("hasNewStyleCategory")
    val hasNewStyleCategory: Boolean,
    @SerializedName("haveService")
    val haveService: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("isFree")
    val isFree: Boolean,
    @SerializedName("isShopOpen")
    val isShopOpen: Boolean,
    @SerializedName("lastActivityDate")
    val lastActivityDate: Any,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("logoImageThumbUrl")
    val logoImageThumbUrl: Any,
    @SerializedName("logoUrl")
    val logoUrl: String,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("lowerLimit")
    val lowerLimit: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("orderNo")
    val orderNo: Int,
    @SerializedName("reviewsCount")
    val reviewsCount: Int,
    @SerializedName("shopEmail")
    val shopEmail: Any,
    @SerializedName("shopPhone")
    val shopPhone: Any,
    @SerializedName("userPromoCode")
    val userPromoCode: Any,
    @SerializedName("workingHours")
    val workingHours: List<WorkingHour>
) {

}