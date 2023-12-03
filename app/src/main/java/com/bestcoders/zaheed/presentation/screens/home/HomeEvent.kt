package com.bestcoders.zaheed.presentation.screens.home

sealed interface HomeEvent {

    data class OnFavoriteClick(val productId:Int, val isFavorite:Boolean) : HomeEvent
    object GetUserLocation: HomeEvent
    data class ShowSnackBar(val message: String, val action: String?= null): HomeEvent

}