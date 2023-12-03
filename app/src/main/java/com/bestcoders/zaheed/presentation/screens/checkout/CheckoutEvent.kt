package com.bestcoders.zaheed.presentation.screens.checkout


sealed interface CheckoutEvent {

    data class GetCartByStore(val storeId:Int) : CheckoutEvent

    data class ChangeProductQuantity(
        val productId: String,
        val variant: String,
        val quantity: Int
    ) : CheckoutEvent

    data class RemoveProductFromCart(
        val productId: String,
        val variant: String,
    ) : CheckoutEvent


    data class ShowSnackBar(val message: String): CheckoutEvent
}