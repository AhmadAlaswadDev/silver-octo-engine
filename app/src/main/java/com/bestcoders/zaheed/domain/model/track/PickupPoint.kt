package com.bestcoders.zaheed.domain.model.track

import com.bestcoders.zaheed.domain.model.products.WorkingHours

data class PickupPoint(
    val id: Int,
    val address: String,
    val latitude: String,
    val longitude: String,
    val phone: String,
    val store: String?,
    val openDays: List<WorkingHours>? = null,
)