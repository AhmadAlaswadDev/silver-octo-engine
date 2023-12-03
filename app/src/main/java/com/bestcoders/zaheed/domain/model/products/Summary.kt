package com.bestcoders.zaheed.domain.model.products


data class Summary(
    var cartTotal: Double,
    val cartTotalBeforeDiscount: Double,
    var cartTotalDiscountAmount: Double,
    val cartItemsCount: Int,
)