package com.bestcoders.zaheed.data.remote.model.order.track

import android.os.Parcelable
import com.bestcoders.zaheed.data.remote.model.products.StoreWithProductsResponse
import com.bestcoders.zaheed.domain.model.track.Order
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderResponse(
    val code: String,
    val combined_order_id: Int,
    val date: String,
    val grand_total: Double,
    val id: Int,
    val order_status: String,
    val order_status_string: String,
    val payment_status: String,
    val payment_status_string: String,
    val payment_type: String,
    val pickup_point: PickupPointResponse?,
    val preferred_time_to_pick_up: String?,
    val shop: StoreWithProductsResponse,
    val total_saving: Double,
    val user_id: Int,
    val order_status_color: String,



) : Parcelable{
    fun toOrder(): Order {
        return Order(
            code = this.code,
            combinedOrderId = this.combined_order_id,
            date = this.date,
            grandTotal = this.grand_total,
            id = this.id,
            orderStatus = this.order_status,
            orderStatusString = this.order_status_string,
            paymentStatus = this.payment_status,
            paymentStatusString = this.payment_status_string,
            paymentType = this.payment_type,
            pickupPoint = this.pickup_point?.toPickupPoint(),
            preferredTimeToPickUp = this.preferred_time_to_pick_up,
            store = this.shop.toStore(),
            totalSaving = this.total_saving,
            userId = this.user_id,
            orderStatusColor = this.order_status_color
        )
    }
}