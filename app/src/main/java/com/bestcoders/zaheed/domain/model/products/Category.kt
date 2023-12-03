package com.bestcoders.zaheed.domain.model.products

data class Category(
    val id: Int,
    val name: String,
    val image: String,
    val numOfShops: String? = null
)
