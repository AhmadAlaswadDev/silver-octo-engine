package com.bestcoders.zaheed.data.remote.model.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateOrderResponse(
    val success: Boolean,
    val `data`: CreateOrderDataResponse? = null,
    val message: String,
    val err: Map<String, List<String>>? = null,
) : Parcelable {
    @Parcelize
    data class CreateOrderDataResponse(
        val order_id: Int
    ) : Parcelable
}