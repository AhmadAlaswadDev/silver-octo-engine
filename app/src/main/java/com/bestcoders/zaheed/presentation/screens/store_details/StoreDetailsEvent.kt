package com.bestcoders.zaheed.presentation.screens.store_details


sealed interface StoreDetailsEvent {

    data class GetStoreDetails(
        val storeId: Int,
        val page:Int,
    ) : StoreDetailsEvent


    data class LoadNextData(
        val storeId: Int,
        val page:Int,
    ) : StoreDetailsEvent
    data class OnFavoriteStoreClick(val storeId: Int, val isFavorite: Boolean) : StoreDetailsEvent

    data class FollowStore(
        val storeId: Int,
    ) : StoreDetailsEvent
    data class UnFollowStore(
        val storeId: Int,
    ) : StoreDetailsEvent

    data class ShowSnackBar(val message: String) :StoreDetailsEvent

}