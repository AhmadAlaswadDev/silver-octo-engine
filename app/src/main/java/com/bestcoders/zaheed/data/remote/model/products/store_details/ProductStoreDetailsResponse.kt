package com.bestcoders.zaheed.data.remote.model.products.store_details

import android.os.Parcelable
import com.bestcoders.zaheed.data.remote.model.products.ShopResponse
import com.bestcoders.zaheed.domain.model.products.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductStoreDetailsResponse(
    val discount: Int,
    val id: Int,
    val is_favorite: Boolean,
    val main_price: Double,
    val name: String,
    val rating: Int,
    val sales: Int,
    val saved: Double,
    val shop: ShopResponse,
    val stroked_price: Int,
    val thumbnail_image: String
) : Parcelable {

    fun toProductStoreDetails(): Product {
        return Product(
            id = this.id,
            discount = this.discount.toString(),
            isFavorite = this.is_favorite,
            mainPrice = this.main_price.toString(),
            name = this.name,
            rating = this.rating,
            sales = this.sales,
            saved = this.saved,
            category = this.shop.toShopCategory(),
            strokedPrice = this.stroked_price.toString(),
            thumbnailImage = this.thumbnail_image,
        )
    }

}