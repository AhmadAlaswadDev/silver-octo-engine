package com.bestcoders.zaheed.data.remote.model.products.cart

import android.os.Parcelable
import com.bestcoders.zaheed.data.remote.model.products.product_details.ColorResponse
import com.bestcoders.zaheed.domain.model.products.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductCartResponse(
    val cart_choices: List<CartChoiceResponse>?,
    val cart_color: ColorResponse?,
    val discount: Int,
    val id: Int,
    val main_price: Double,
    val name: String,
    val quantity: Int,
    val rating: Int,
    val sales: Int,
    val saved: Double,
    val stroked_price: Double,
    val thumbnail_image: String,
    val variant: String
) : Parcelable {
    fun toProductCart(): Product {
        return Product(
            cartChoices = this.cart_choices?.map { it.toCartChoice() },
            cartColor = this.cart_color?.toColor(),
            discount = this.discount.toString(),
            id = this.id,
            mainPrice = this.main_price.toString(),
            name = this.name,
            quantity = this.quantity,
            rating = this.rating,
            sales = this.sales,
            saved = this.saved,
            strokedPrice = this.stroked_price.toString(),
            thumbnailImage = this.thumbnail_image,
            variant = this.variant,
        )
    }
}