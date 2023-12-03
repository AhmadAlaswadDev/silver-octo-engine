package com.bestcoders.zaheed.data.remote.model.products.product_details

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.ProductDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetailsResponse(
    val success: Boolean,
    val `data`: ProductDetailsDataResponse? = null,
    val err: Map<String, List<String>>? = null
) : Parcelable {
    fun toProductDetails(): ProductDetails {
        return ProductDetails(
            id = this.data!!.id,
            availableVariations = this.data.available_variations,
            category = this.data.category.toShopCategory(),
            choices = this.data.choices?.map { it.toChoice() } ?: emptyList(),
            colors = this.data.colors.map { it.toColor() },
            description = this.data.description ?: "",
            discount = this.data.discount,
            isFavorite = this.data.is_favorite,
            mainPrice = this.data.main_price,
            name = this.data.name,
            saved = this.data.saved,
            selectedVariant = this.data.selected_variant,
            shop = this.data.shop.toStoreProductDetails(),
            slides = this.data.slides.toSlide(),
            strokedPrice = this.data.stroked_price,
            offerStartDate = this.data.offer_start_date,
            offerEndDate = this.data.offer_end_date,
            itemsLeft = this.data.items_left,
            offerStatus = this.data.offer_status,
        )
    }
}