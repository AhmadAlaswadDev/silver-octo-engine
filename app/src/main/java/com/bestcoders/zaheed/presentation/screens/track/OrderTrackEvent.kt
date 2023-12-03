package com.bestcoders.zaheed.presentation.screens.track

sealed interface OrderTrackEvent {

    data class ShowSnackBar(val message: String, val action: String? = null) : OrderTrackEvent

    data class GetOrderTrack(val orderId: Int) : OrderTrackEvent


}