package com.bestcoders.zaheed.presentation.screens.cart

import com.bestcoders.zaheed.domain.model.products.CartModel

data class CartState(
    val isLoading: Boolean = false,
    val addProductToCartSuccess: Boolean = false,
    val removeProductFromCartSuccess: Boolean = false,
    val getCartsSuccess: Boolean = false,
    val cartModel: CartModel? = null,
    val error: String? = null,
    val userLatitude: Double = 0.0,
    val userLongitude: Double = 0.0,
)
