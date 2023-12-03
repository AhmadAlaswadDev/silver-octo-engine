package com.bestcoders.zaheed.domain.model.track

import com.bestcoders.zaheed.domain.model.products.Store

data class Order(
    val code: String,
    val combinedOrderId: Int,
    val date: String,
    val grandTotal: Double,
    val id: Int,
    val orderStatus: String,
    val orderStatusString: String,
    val paymentStatus: String,
    val paymentStatusString: String,
    val paymentType: String,
    val pickupPoint: PickupPoint?,
    val preferredTimeToPickUp: String?,
    val store: Store,
    val totalSaving: Double,
    val userId: Int,
    val orderStatusColor: String,
)