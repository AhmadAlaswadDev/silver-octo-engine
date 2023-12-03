package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import androidx.compose.runtime.toMutableStateList
import com.bestcoders.zaheed.domain.model.products.HomeLayout
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeLayoutResponse(
    val success: Boolean,
    val `data`: HomeLayoutDataResponse? = null,
    val err: Map<String, List<String>>? = null,
    val message: String
) : Parcelable {

    fun toHomeLayout(): HomeLayout {
        return HomeLayout(
            bestSales = this.data?.best_sales?.map { it.toBestSaleProduct() }?.toMutableStateList(),
            categories = this.data?.categories?.map { it.toCategory() }?.toMutableStateList(),
            homeBanner = this.data?.home_banner?.map { it.toHomeBanner() }?.toMutableStateList(),
            nearbyStores = this.data?.nearby?.map { it.toStore() }?.toMutableStateList(),
            paginationMeta = this.data?.pagination_meta?.toPaginationMeta(),
        )
    }
}

