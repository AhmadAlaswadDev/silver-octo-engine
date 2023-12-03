package com.bestcoders.zaheed.data.remote.model.order

import com.bestcoders.zaheed.data.remote.model.order.track.OrderResponse

data class GetPurchaseHistoryDetailsResponse(
    val success: Boolean,
    val `data`: OrderResponse? = null,
    val message: String? = null,
    val errors: Map<String, List<String>>? = null,
)
