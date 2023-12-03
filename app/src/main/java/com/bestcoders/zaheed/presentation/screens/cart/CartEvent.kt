package com.bestcoders.zaheed.presentation.screens.cart


sealed interface CartEvent {

    data class AddProductToCart(
        val productId: Int,
        val variant: String,
        val quantity: Int
    ) : CartEvent



    data class RemoveProductFromCart(
        val productId: Int,
        val variant: String,
    ) : CartEvent


    object GetCarts : CartEvent

    data class ShowSnackBar(val message: String): CartEvent


}