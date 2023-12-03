package com.bestcoders.zaheed.domain.model.products

data class StoreDetails(
    val id: Int,
    val address: String,
    val branchName: String,
    val distance: Double,
    var isFavorite: Boolean,
    var isSubscribed: Boolean,
    val latitude: String,
    val logo: String,
    val longitude: String,
    val name: String,
    val refundPolicy: String,
    val saved: Double,
    val category: ShopCategory,
    val sliders: List<String>,
    val sections: List<StoreSection>,
    val all: AllProductsStoreDetails,
    val workingHours: List<WorkingHours>
)