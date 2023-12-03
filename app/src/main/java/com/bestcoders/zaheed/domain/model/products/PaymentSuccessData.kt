package com.bestcoders.zaheed.domain.model.products

import com.bestcoders.zaheed.domain.model.track.PickupPoint

data class PaymentSuccessData(
    val cartByStoreModel: CartByStoreModel,
    val orderId: Int,
    val pickupPoint: PickupPoint?,
    val pickupDate: String,
    val pickupTime: String,
)
