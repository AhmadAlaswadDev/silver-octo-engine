package com.bestcoders.zaheed.presentation.screens.profile_screens.order_details

import com.bestcoders.zaheed.domain.model.track.Order

data class OrderDetailsState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val orderDetails: Order? = null,
)