package com.bestcoders.zaheed.presentation.screens.profile_screens.order_details

sealed interface OrderDetailsEvent {
    data class ShowSnackBar(val message: String) : OrderDetailsEvent

    data class GetOrderDetails(
        val orderId: Int,
    ) : OrderDetailsEvent

}