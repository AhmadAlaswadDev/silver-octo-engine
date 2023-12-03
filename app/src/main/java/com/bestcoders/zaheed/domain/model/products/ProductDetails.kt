package com.bestcoders.zaheed.domain.model.products

data class ProductDetails(
    val id: Int,
    val availableVariations: List<String>,
    val category: ShopCategory,
    val choices: List<Choice>,
    val colors: List<ColorModel>,
    val description: String,
    val discount: Int,
    var isFavorite: Boolean,
    val mainPrice: Double,
    val name: String,
    val saved: Double,
    val selectedVariant: String,
    val shop: StoreProductDetails,
    val slides: Slide,
    val strokedPrice: Double,
    val offerStartDate: String,
    val offerEndDate: String,
    val itemsLeft: Int,
    val offerStatus: String,
)