package com.bestcoders.zaheed.data.remote.model.order.track

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.track.Track
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackResponse(
    val is_order_completed: Boolean,
    val is_order_confirmed: Boolean,
    val is_order_placed: Boolean,
    val is_order_ready_to_pick_up: Boolean,
    val order_completed_at: String?,
    val order_confirmed_at: String?,
    val order_placed_at: String,
    val order_ready_to_pick_up_at: String?
) : Parcelable {
    fun toTrack(): Track {
        return Track(
            isOrderCompleted = this.is_order_completed,
            isOrderConfirmed = this.is_order_confirmed,
            isOrderPlaced = this.is_order_placed,
            isOrderReadyToPickUp = this.is_order_ready_to_pick_up,
            orderCompletedAt = this.order_completed_at,
            orderConfirmedAt = this.order_confirmed_at,
            orderPlacedAt = this.order_placed_at,
            orderReadyToPickUpAt = this.order_ready_to_pick_up_at,
        )
    }
}