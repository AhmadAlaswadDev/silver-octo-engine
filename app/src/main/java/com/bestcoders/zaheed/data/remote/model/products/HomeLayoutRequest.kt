package com.bestcoders.zaheed.data.remote.model.products

import com.google.gson.annotations.SerializedName

data class HomeLayoutRequest(
    @SerializedName("amount_of_discount") val amountOfDiscount: String,
    @SerializedName("sort_by") val sortBy: String,
    @SerializedName("price_range_min") val priceRangeMin: String,
    @SerializedName("price_range_max") val priceRangeMax: String,
    @SerializedName("distance") val distance: String,
)