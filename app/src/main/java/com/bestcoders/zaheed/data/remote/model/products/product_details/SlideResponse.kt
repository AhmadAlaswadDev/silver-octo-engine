package com.bestcoders.zaheed.data.remote.model.products.product_details

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.Slide
import kotlinx.parcelize.Parcelize

@Parcelize
data class SlideResponse(
    val offer: List<String>
) : Parcelable {
    fun toSlide(): Slide {
        return Slide(
            offer = this.offer,
        )
    }
}