package com.bestcoders.zaheed.domain.model.products

import com.bestcoders.zaheed.core.util.Constants

data class OrderFilter(
    val orderFiltersStatus: String = Constants.settings.orderFiltersStatus[0].value,
    val orderPaymentStatus: String = Constants.settings.orderPaymentStatus[0].value,
)

