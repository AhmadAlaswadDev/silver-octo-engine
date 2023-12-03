package com.bestcoders.zaheed.presentation.screens.profile_screens.order_history

sealed interface OrderHistoryEvent {
    data class ShowSnackBar(val message: String) : OrderHistoryEvent

    data class GetOrderHistory(
        val page: Int,
        val paymentStatus: String,
        val orderStatus: String,
        val searchValue: String,
    ) : OrderHistoryEvent

}