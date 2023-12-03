package com.bestcoders.zaheed.data.remote.model.order

import com.google.gson.annotations.SerializedName

data class CreateOrderRequest(
    @SerializedName("shop_id") val storeId: String,
    @SerializedName("payment_type") val paymentType: String,
    @SerializedName("pickup_id") val pickupLocationId: String,
    @SerializedName("preferred_time_to_pick_up") val preferredTimeToPickUp: String
)
