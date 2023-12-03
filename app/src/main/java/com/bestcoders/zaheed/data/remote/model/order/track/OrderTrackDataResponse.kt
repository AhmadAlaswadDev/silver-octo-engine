package com.bestcoders.zaheed.data.remote.model.order.track

data class OrderTrackDataResponse(
    val order: OrderResponse,
    val pickup_point: PickupPointResponse,
    val track: TrackResponse
)