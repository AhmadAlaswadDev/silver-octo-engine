package com.bestcoders.zaheed.data.remote.model.order.track

import com.bestcoders.zaheed.domain.model.track.OrderTrack

data class OrderTrackResponse(
    val success: Boolean,
    val `data`: OrderTrackDataResponse,
    val message: String? = null,
) {
    fun toOrderTrack(): OrderTrack {
        return OrderTrack(
            order = this.data.order.toOrder(),
            pickupPoint = this.data.pickup_point.toPickupPoint(),
            track = this.data.track.toTrack(),
        )
    }

}