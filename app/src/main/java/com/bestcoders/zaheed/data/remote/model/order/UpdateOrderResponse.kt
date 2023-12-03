package com.bestcoders.zaheed.data.remote.model.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateOrderResponse(
    val success: Boolean,
    val `data`: UpdateOrderDataResponse? = null,
    val message: String,
    val err: Map<String, List<String>>? = null,
) : Parcelable {
    @Parcelize
    data class UpdateOrderDataResponse(
        val order_id: Int
    ) : Parcelable
}