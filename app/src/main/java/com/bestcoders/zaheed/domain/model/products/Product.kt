package com.bestcoders.zaheed.domain.model.products

data class Product(
    val cartChoices: List<CartChoice>? = null,
    val cartColor: ColorModel? = null,
    val discount: String,
    val id: Int,
    val mainPrice: String,
    val name: String,
    var quantity: Int? = null,
    val rating: Int,
    val sales: Int,
    val saved: Double? = null,
    val strokedPrice: String,
    val thumbnailImage: String,
    val variant: String? = null,
    var isFavorite: Boolean? = null,
    val category: ShopCategory? = null,

    )