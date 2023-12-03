package com.bestcoders.zaheed.data.remote.model.products.product_details

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.ColorModel
import kotlinx.parcelize.Parcelize


@Parcelize
data class ColorResponse(
    val code: String,
    val name: String,
    val origial_name: String
): Parcelable{
    fun toColor():ColorModel {
        return ColorModel(
            code = this.code,
            name = this.name,
            originalName = this.origial_name
        )
    }
}