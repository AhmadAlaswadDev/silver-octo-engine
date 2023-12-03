package com.bestcoders.zaheed.data.remote.model.products.product_details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetailsDataResponse(
    val description: String?,
    val discount: Int,
    val id: Int,
    val is_favorite: Boolean,
    val main_price: Double,
    val name: String,
    val saved: Double,
    val stroked_price: Double,
    val selected_variant: String,
    val shop: StoreProductDetailsResponse,
    val available_variations: List<String>,
    val offer_start_date: String,
    val offer_end_date: String,
    val items_left: Int,
    val offer_status: String,
    val category: CategoryProductDetailsResponse,
    val choices: List<ChoiceResponse>,
    val colors: List<ColorResponse>,
    val slides: SlideResponse,
): Parcelable