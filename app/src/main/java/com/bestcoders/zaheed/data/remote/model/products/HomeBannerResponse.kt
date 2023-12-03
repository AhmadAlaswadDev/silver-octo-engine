package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.HomeBanner
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeBannerResponse(
    val effect: String,
    val item_id: Int,
    val photo: String
): Parcelable {
    fun toHomeBanner(): HomeBanner {
        return HomeBanner(
            effect = this.effect,
            itemId = this.item_id,
            photo = this.photo
        )
    }
}