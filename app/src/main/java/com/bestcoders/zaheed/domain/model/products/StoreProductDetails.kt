package com.bestcoders.zaheed.domain.model.products

data class StoreProductDetails(
    val id: Int,
    val name: String,
    val logo: String,
    val address: String,
    val branchName: String,
    val distance: Double
)