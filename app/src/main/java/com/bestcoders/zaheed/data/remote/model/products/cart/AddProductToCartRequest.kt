package com.bestcoders.zaheed.data.remote.model.products.cart

import com.google.gson.annotations.SerializedName


data class AddProductToCartRequest(
    @SerializedName("id") val id: Int,
    @SerializedName("variant") val variant: String,
    @SerializedName("quantity") val quantity: Int,
)