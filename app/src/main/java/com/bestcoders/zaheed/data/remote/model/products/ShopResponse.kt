package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.ShopCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopResponse(
    val id: Int,
    val name: String
) : Parcelable {
    fun toShopCategory(): ShopCategory {
        return ShopCategory(
            id = this.id,
            name = this.name
        )
    }
}