package com.bestcoders.zaheed.presentation.screens.map

sealed interface MapEvent {

    data class ShowSnackBar(val message: String, val action: String?= null): MapEvent

}