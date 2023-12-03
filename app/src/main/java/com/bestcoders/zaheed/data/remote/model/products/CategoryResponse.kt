package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.Category
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryResponse(
    val id: Int,
    val image: String,
    val name: String,
    val num_of_shops: String?
) : Parcelable {
    fun toCategory(): Category {
        return Category(
            id = this.id,
            image = this.image,
            name = this.name,
            numOfShops = this.num_of_shops
        )
    }
}