package com.bestcoders.zaheed.presentation.screens.payment_success

sealed interface PaymentSuccessEvent {
    data class ShowSnackBar(val message: String) : PaymentSuccessEvent

    data class GetOrderDetails(val orderId: Int, ) : PaymentSuccessEvent

}