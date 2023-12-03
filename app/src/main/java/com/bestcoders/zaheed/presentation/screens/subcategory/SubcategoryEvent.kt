package com.bestcoders.zaheed.presentation.screens.subcategory

import com.bestcoders.zaheed.core.util.Constants

sealed interface SubcategoryEvent {

    data class ShowSnackBar(val message: String, val action: String? = null) : SubcategoryEvent

    data class GetSubcategory(
        val subcategoryId: Int,
        val page: Int,
        val amountOfDiscount: String = Constants.settings.layoutsFiltersDefaultDiscount,
        val sortBy: String = Constants.settings.layoutsFiltersDefaultSortBy,
        val priceRangeMax: String = Constants.settings.layoutsFiltersDefaultMaxPrice,
        val priceRangeMin: String = Constants.settings.layoutsFiltersDefaultMinPrice,
    ) : SubcategoryEvent

    data class LoadNextItems(
        val subcategoryId: Int,
        val page: Int,
        val amountOfDiscount: String = Constants.settings.layoutsFiltersDefaultDiscount,
        val sortBy: String = Constants.settings.layoutsFiltersDefaultSortBy,
        val priceRangeMax: String = Constants.settings.layoutsFiltersDefaultMaxPrice,
        val priceRangeMin: String = Constants.settings.layoutsFiltersDefaultMinPrice,
    ) : SubcategoryEvent

    data class OnFavoriteProductClick(val productId: Int, val isFavorite: Boolean) :
        SubcategoryEvent

}