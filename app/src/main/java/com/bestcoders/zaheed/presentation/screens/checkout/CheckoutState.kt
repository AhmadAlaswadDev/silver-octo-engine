package com.bestcoders.zaheed.presentation.screens.checkout

import com.bestcoders.zaheed.domain.model.products.CartByStoreModel

data class CheckoutState(
    val isLoading: Boolean = false,
    val getCartByStoreSuccess: Boolean = false,
    val cartByStoreModel: CartByStoreModel? = null,
    val changeProductQuantitySuccess: Boolean = false,
    val removeProductFromCartSuccess: Boolean = false,
    var error: String? = null,
    val userLatitude: Double = 0.0,
    val userLongitude: Double = 0.0,
)
