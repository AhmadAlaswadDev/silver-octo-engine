package com.bestcoders.zaheed.data.remote.model.products.cart

import android.os.Parcelable
import com.bestcoders.zaheed.data.remote.model.products.store_details.WorkingHoursResponse
import com.bestcoders.zaheed.domain.model.products.Cart
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartResponse(
    val address: String,
    val cart_total_before_discount: Double,
    val cart_total_saving: Double,
    val distance: Double,
    val id: Int,
    val is_favorite: Boolean,
    val is_subscribed: Boolean,
    val logo: String,
    val name: String,
    val products: List<ProductCartResponse>,
    val rating: Int,
    val saved: Double,
    val has_pending_order: Boolean,
    val order_id: Int? = null,
    val working_hours: List<WorkingHoursResponse>
) : Parcelable {
    fun toCart(): Cart {
        return Cart(
            address = this.address,
            cartTotalBeforeDiscount = this.cart_total_before_discount,
            cartTotalSaving = this.cart_total_saving,
            distance = this.distance,
            id = this.id,
            isFavorite = this.is_favorite,
            isSubscribed = this.is_subscribed,
            logo = this.logo,
            name = this.name,
            products = this.products.map { it.toProductCart() }.toMutableList(),
            rating = this.rating,
            saved = this.saved,
            hasPendingOrder = this.has_pending_order,
            orderId = this.order_id,
            workingHours = this.working_hours.map { it.toWorkingHours() },
        )
    }
}