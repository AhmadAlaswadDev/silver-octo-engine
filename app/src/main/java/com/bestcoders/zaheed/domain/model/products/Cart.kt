package com.bestcoders.zaheed.domain.model.products

data class Cart(
    val address: String,
    val cartTotalBeforeDiscount: Double,
    val cartTotalSaving: Double,
    val distance: Double,
    val id: Int,
    val isFavorite: Boolean,
    val isSubscribed: Boolean,
    val logo: String,
    val name: String,
    val products: MutableList<Product>,
    val rating: Int,
    val saved: Double,
    val hasPendingOrder: Boolean,
    val orderId: Int? = null,
    val workingHours: List<WorkingHours>
)