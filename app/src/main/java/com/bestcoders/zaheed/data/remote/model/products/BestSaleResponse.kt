package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.BestSale
import kotlinx.parcelize.Parcelize

@Parcelize
data class BestSaleResponse(
    val id: Int,
    val name: String,
    val thumbnail_image: String,
    val discount: String,
    val sales: Int,
    val rating: Int,
    val stroked_price: String,
    val main_price: String,
    val is_favorite: Boolean,
    val shop: ShopResponse
) : Parcelable {
    fun toBestSaleProduct(): BestSale {
        return BestSale(
            discount = this.discount,
            id = this.id,
            isFavorite = this.is_favorite,
            mainPrice = this.main_price,
            name = this.name,
            rating = this.rating,
            sales = this.sales,
            shopCategory = this.shop.toShopCategory(),
            strokedPrice = this.stroked_price,
            thumbnailImage = this.thumbnail_image,
        )
    }
}