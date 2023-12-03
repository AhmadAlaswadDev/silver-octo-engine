package com.bestcoders.zaheed.domain.model.products

data class StoreSection(
    val sectionId: Int,
    val sectionName: String,
    val productsCount: Int,
    val products: MutableList<Product>
)