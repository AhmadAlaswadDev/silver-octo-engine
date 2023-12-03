package com.bestcoders.zaheed.data.remote.model.products.cart

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.CartModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCartsResponse(
    val success: Boolean,
    val `data`: CartsDataResponse? = null,
    val message: String? = null,
    val err: List<String>? = null,
) : Parcelable {
    fun toCartModel(): CartModel {
        return CartModel(
            carts = this.data?.carts?.map { it.toCart() } ?: emptyList(),
            summary = this.data!!.summary!!.toSummary(),
        )
    }
}