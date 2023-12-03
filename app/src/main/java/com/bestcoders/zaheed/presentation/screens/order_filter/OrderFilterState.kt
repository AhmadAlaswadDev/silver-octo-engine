package com.bestcoders.zaheed.presentation.screens.order_filter

import com.bestcoders.zaheed.core.util.Constants

data class OrderFilterState(
    val isLoading: Boolean = false,
    val orderFiltersStatus: String = Constants.settings.orderFiltersStatus[0].value,
    val orderPaymentStatus: String = Constants.settings.orderPaymentStatus[0].value,
)
