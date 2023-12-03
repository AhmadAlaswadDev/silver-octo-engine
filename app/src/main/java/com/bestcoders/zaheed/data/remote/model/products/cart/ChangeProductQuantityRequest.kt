package com.bestcoders.zaheed.data.remote.model.products.cart

import com.google.gson.annotations.SerializedName


data class ChangeProductQuantityRequest(
    @SerializedName("id") val id: String,
    @SerializedName("quantity") val quantity: String,
    @SerializedName("variant") val variant: String,
)