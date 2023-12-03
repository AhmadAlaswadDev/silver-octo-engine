package com.bestcoders.zaheed.presentation.screens.home_container

sealed interface HomeContainerEvent {
    data class LoadNextItems(
        val page: Int,
        val amountOfDiscount: String,
        val sortBy: String,
        val priceRangeMax: String,
        val priceRangeMin: String,
    ) : HomeContainerEvent

    object GetUserLocationWithHomeData : HomeContainerEvent

    data class GetHomeLayoutWithCustomLocation(val latitude: Double, val longitude: Double) :
        HomeContainerEvent

    data class GetHomeLayoutWithFilter(
        val page: Int,
        val amountOfDiscount: String,
        val sortBy: String,
        val priceRangeMax: String,
        val priceRangeMin: String,
    ) : HomeContainerEvent

    data class ShowSnackBar(val message: String, val action: String? = null) : HomeContainerEvent

}