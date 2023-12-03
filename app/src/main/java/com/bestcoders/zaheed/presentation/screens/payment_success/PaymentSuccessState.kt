package com.bestcoders.zaheed.presentation.screens.payment_success

import com.bestcoders.zaheed.domain.model.track.Order

data class PaymentSuccessState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val orderDetails: Order? = null,
)