package com.bestcoders.zaheed.presentation.screens.product_details

import com.bestcoders.zaheed.domain.model.products.ProductDetails
import com.bestcoders.zaheed.domain.model.products.ProductVariationDetails

data class ProductDetailsState(
    val isLoading: Boolean = false,
    val getProductDetails: Boolean = false,
    val addProductToCartSuccess: Boolean = false,
    val getProductVariationDetailsSuccess: Boolean = false,
    val error: String? = null,
    val productDetails: ProductDetails? = null,
    val productVariationDetails: ProductVariationDetails? = null,
    val userLatitude: Double = 0.0,
    val userLongitude: Double = 0.0,
    val isProductAddedToFavorite: Boolean = false,
    val isProductRemovedFromFavorite: Boolean = false,
)
