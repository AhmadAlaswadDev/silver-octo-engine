package com.bestcoders.zaheed.domain.model.track

data class OrderTrack(
    val order: Order,
    val pickupPoint: PickupPoint,
    val track: Track
)