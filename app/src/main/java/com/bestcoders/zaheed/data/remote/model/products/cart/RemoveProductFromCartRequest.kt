package com.bestcoders.zaheed.data.remote.model.products.cart

import com.google.gson.annotations.SerializedName


data class RemoveProductFromCartRequest(
    @SerializedName("id") val id: Int,
    @SerializedName("variant") val variant: String,
)