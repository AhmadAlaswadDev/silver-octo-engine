package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import androidx.compose.runtime.toMutableStateList
import com.bestcoders.zaheed.domain.model.products.Store
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreWithProductsResponse(
    val id: Int,
    val name: String,
    val logo: String,
    val rating: Int,
    val distance: Double,
    val address: String,
    val saved: String,
    val is_favorite: Boolean,
    val shop_category: ShopResponse?,
    val products: MutableList<ProductResponse>?,
    val branch_name: String?,
    val is_subscribed: Boolean?,
) : Parcelable {
    fun toStore(): Store {
        return Store(
            id = this.id,
            address = this.address,
            distance = this.distance,
            isFavorite = this.is_favorite,
            logo = this.logo,
            name = this.name,
            products = this.products?.map { it.toProduct() }?.toMutableStateList(),
            rating = this.rating,
            saved = this.saved,
            category = this.shop_category?.toShopCategory(),
            branchName = this.branch_name,
            isSubscribed = this.is_subscribed,
        )
    }
}