package com.bestcoders.zaheed.presentation.screens.map

data class MapState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val userLatitude: Double = 0.0,
    val userLongitude: Double = 0.0,
    val error: String? = null,
)
