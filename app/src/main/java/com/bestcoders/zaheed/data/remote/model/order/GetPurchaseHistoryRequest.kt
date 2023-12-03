package com.bestcoders.zaheed.data.remote.model.order


import com.google.gson.annotations.SerializedName

data class GetPurchaseHistoryRequest(
    @SerializedName("payment_status") val paymentStatus: String,
    @SerializedName("order_status") val orderStatus: String,
    @SerializedName("search") val searchValue: String,
)
