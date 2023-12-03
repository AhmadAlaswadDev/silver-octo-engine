package com.bestcoders.zaheed.data.remote.model.order

import com.google.gson.annotations.SerializedName

data class GetTelrUrlRequest(
    @SerializedName("combined_order_id") val orderId: String,
)
