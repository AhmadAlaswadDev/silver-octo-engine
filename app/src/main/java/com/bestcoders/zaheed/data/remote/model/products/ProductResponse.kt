package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import com.bestcoders.zaheed.data.remote.model.products.cart.CartChoiceResponse
import com.bestcoders.zaheed.data.remote.model.products.product_details.ColorResponse
import com.bestcoders.zaheed.domain.model.products.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductResponse(
    val discount: String,
    val id: Int,
    val main_price: String,
    val name: String,
    val rating: Int,
    val sales: Int,
    val stroked_price: String,
    val thumbnail_image: String,
    val is_favorite: Boolean,
    val cart_choices: List<CartChoiceResponse>?,
    val cart_color: ColorResponse?,
    val quantity: Int?,
    val saved: Double?,
    val variant: String?,
) : Parcelable {
    fun toProduct(): Product {
        return Product(
            id = this.id,
            name = this.name,
            thumbnailImage = this.thumbnail_image,
            discount = this.discount,
            sales = this.sales,
            rating = this.rating,
            strokedPrice = this.stroked_price,
            mainPrice = this.main_price,
            isFavorite = this.is_favorite,
            cartChoices = this.cart_choices?.map { it.toCartChoice() },
            cartColor = this.cart_color?.toColor(),
            quantity = this.quantity,
            saved = this.saved,
            variant = this.variant,
        )
    }
}