package com.bestcoders.zaheed.domain.model.track

data class Track(
    val isOrderCompleted: Boolean,
    val isOrderConfirmed: Boolean,
    val isOrderPlaced: Boolean,
    val isOrderReadyToPickUp: Boolean,
    val orderCompletedAt: String?,
    val orderConfirmedAt: String?,
    val orderPlacedAt: String,
    val orderReadyToPickUpAt: String?
)