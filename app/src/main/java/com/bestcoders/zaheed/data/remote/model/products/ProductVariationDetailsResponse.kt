package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.ProductVariationDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductVariationDetailsResponse(
    val success: Boolean,
    val `data`: Data?,
    val message: String
) : Parcelable {
    @Parcelize
    data class Data(
        val discount: Int,
        val main_price: Double,
        val saved: Double,
        val stroked_price: Double
    ) : Parcelable

    fun toProductVariationDetails(): ProductVariationDetails {
        return ProductVariationDetails(
            discount = this.data!!.discount,
            mainPrice = this.data.main_price,
            saved = this.data.saved,
            strokedPrice = this.data.stroked_price,
        )
    }
}