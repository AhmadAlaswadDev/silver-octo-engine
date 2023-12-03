package com.bestcoders.zaheed.data.remote.model.products

import com.google.gson.annotations.SerializedName

data class ProductVariationDetailsRequest(
    @SerializedName("variant") val variant: String,
)
