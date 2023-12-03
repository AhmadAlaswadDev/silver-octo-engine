package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeLayoutDataResponse(
    val home_banner: List<HomeBannerResponse>?,
    val categories: List<CategoryResponse>?,
    val best_sales: List<BestSaleResponse>,
    val nearby: List<StoreWithProductsResponse>,
    val pagination_meta: PaginationMetaResponse
): Parcelable