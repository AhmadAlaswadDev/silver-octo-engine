package com.bestcoders.zaheed.presentation.screens.confirm_order


sealed interface ConfirmOrderEvent {

    data class GetCartByStore(val storeId: Int) : ConfirmOrderEvent
    data class GetStorePickupLocations(val storeId: Int) : ConfirmOrderEvent

    data class CreateOrder(
        val storeId: String,
        val paymentType: String,
        val preferredTimeToPickUp: String,
        val pickupLocationId: String,
    ) : ConfirmOrderEvent

    data class UpdateOrder(
        val orderId: String,
        val paymentType: String,
        val preferredTimeToPickUp: String,
        val pickupLocationId: String,
    ) : ConfirmOrderEvent


    data class ShowSnackBar(val message: String) : ConfirmOrderEvent

}