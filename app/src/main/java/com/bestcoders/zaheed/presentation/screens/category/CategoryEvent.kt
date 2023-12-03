package com.bestcoders.zaheed.presentation.screens.category

import com.bestcoders.zaheed.core.util.Constants

sealed interface CategoryEvent {

    data class ShowSnackBar(val message: String, val action: String?= null): CategoryEvent
    data class GetCategory(
        val categoryId: Int,
        val page: Int,
        val amountOfDiscount: String = Constants.settings.layoutsFiltersDefaultDiscount,
        val sortBy: String = Constants.settings.layoutsFiltersDefaultSortBy,
        val priceRangeMax: String = Constants.settings.layoutsFiltersDefaultMaxPrice,
        val priceRangeMin: String = Constants.settings.layoutsFiltersDefaultMinPrice,
    ) : CategoryEvent
    data class LoadNextItems(
        val categoryId: Int,
        val page: Int,
        val amountOfDiscount: String = Constants.settings.layoutsFiltersDefaultDiscount,
        val sortBy: String = Constants.settings.layoutsFiltersDefaultSortBy,
        val priceRangeMax: String = Constants.settings.layoutsFiltersDefaultMaxPrice,
        val priceRangeMin: String = Constants.settings.layoutsFiltersDefaultMinPrice,
    ) : CategoryEvent

    data class OnFavoriteProductClick(val productId: Int, val isFavorite: Boolean) : CategoryEvent

}