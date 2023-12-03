package com.bestcoders.zaheed.presentation.screens.favorite

sealed interface FavoriteEvent {

    data class OnFavoriteProductClick(val productId: Int, val isFavorite: Boolean) : FavoriteEvent
    data class OnFavoriteStoreClick(val storeId: Int, val isFavorite: Boolean) : FavoriteEvent

    data class GetFavoriteProducts(val page: Int) : FavoriteEvent
    data class LoadNextItemsProducts(val page: Int) : FavoriteEvent

    data class GetFavoriteStores(val page: Int) : FavoriteEvent
    data class LoadNextItemsStores(val page: Int) : FavoriteEvent

    data class ShowSnackBar(val message: String) : FavoriteEvent

}