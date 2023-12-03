package com.bestcoders.zaheed.presentation.screens.confirm_order

import com.bestcoders.zaheed.domain.model.products.CartByStoreModel
import com.bestcoders.zaheed.domain.model.track.PickupPoint

data class ConfirmOrderState(
    val isLoading: Boolean = false,
    val getCartByStoreSuccess: Boolean = false,
    val cartByStoreModel: CartByStoreModel? = null,
    val pickupPoints: List<PickupPoint>? = null,
    val createOrderSuccess: Boolean = false,
    val updateOrderSuccess: Boolean = false,
    val storePickupLocationsSuccess: Boolean = false,
    val getTelrUrlSuccess: Boolean = false,
    val orderId: Int? = null,
    val telrUrl: String? = null,
    val error: String? = null,
    val userLatitude: Double = 0.0,
    val userLongitude: Double = 0.0,
)
