package com.bestcoders.zaheed.data.remote.model.products.cart

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.Summary
import kotlinx.parcelize.Parcelize

@Parcelize
data class SummaryResponse(
    val cart_total: Double,
    val cart_total_before_discount: Double,
    var cart_total_discount_amount: Double,
    val cart_items_count: Int,
) : Parcelable {
    fun toSummary(): Summary {
        return Summary(
            cartTotal = cart_total,
            cartTotalBeforeDiscount = cart_total_before_discount,
            cartTotalDiscountAmount = cart_total_discount_amount,
            cartItemsCount = cart_items_count,
        )
    }
}