package com.bestcoders.zaheed.presentation.screens.track

import com.bestcoders.zaheed.domain.model.track.OrderTrack

data class OrderTrackState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val orderTrack: OrderTrack? = null,
)
