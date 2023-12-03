package com.bestcoders.zaheed.presentation.screens.product_details


sealed interface ProductDetailsEvent {

    data class GetProductDetails(
        val productId: Int,
    ) : ProductDetailsEvent

    data class AddProductToCart(
        val productId: Int,
        val variant: String,
        val quantity: Int,
    ) : ProductDetailsEvent

    data class GetProductVariationDetails(
        val variant: String,
        val productId: Int,
    ) : ProductDetailsEvent

    data class OnFavoriteProductClick(val productId: Int, val isFavorite: Boolean) : ProductDetailsEvent

    data class ShowSnackBar(val message: String) : ProductDetailsEvent

}