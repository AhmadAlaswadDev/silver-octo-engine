package com.bestcoders.zaheed.data.remote.model.products.cart

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.CartByStoreModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCartByStoreResponse(
    val success: Boolean,
    val `data`: CartByStoreDataResponse? = null,
    val message: String? = null,
    val err: Map<String, List<String>>? = null,
) : Parcelable {
    fun toCartByStoreModel(): CartByStoreModel {
        return CartByStoreModel(
            cart = this.data!!.cart.toCart(),
            summary = this.data.summary.toSummary(),
        )
    }
}