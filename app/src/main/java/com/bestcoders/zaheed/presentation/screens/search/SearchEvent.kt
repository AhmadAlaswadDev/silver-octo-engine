package com.bestcoders.zaheed.presentation.screens.search

import com.bestcoders.zaheed.presentation.screens.favorite.FavoriteEvent

sealed interface SearchEvent {

    data class OnFavoriteProductClick(val productId: Int, val isFavorite: Boolean) : SearchEvent
    data class OnFavoriteStoreClick(val storeId: Int, val isFavorite: Boolean) : SearchEvent

    data class GetSearchedStores(val page:Int, val storeName: String) : SearchEvent
    data class LoadNextItemsStores(val page: Int, val storeName: String) : SearchEvent

    data class GetSearchedProducts(val page:Int, val productName: String) : SearchEvent

    data class LoadNextItemsProducts(val page: Int, val productName: String) : SearchEvent

    data class ShowSnackBar(val message: String, val action: String?= null): SearchEvent
}