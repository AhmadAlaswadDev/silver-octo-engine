package com.bestcoders.zaheed.presentation.screens.filter

import com.bestcoders.zaheed.core.util.Constants

data class FilterState(
    val isLoading: Boolean = false,
    val amountOfDiscount:String = Constants.settings.layoutsFiltersDefaultDiscount,
    val sortBy:String = Constants.settings.layoutsFiltersDefaultSortBy,
    val priceRangeMax: String = Constants.settings.layoutsFiltersDefaultMaxPrice,
    val priceRangeMin: String = Constants.settings.layoutsFiltersDefaultMinPrice
)
