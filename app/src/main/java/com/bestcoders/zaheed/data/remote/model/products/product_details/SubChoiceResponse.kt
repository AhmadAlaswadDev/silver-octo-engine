package com.bestcoders.zaheed.data.remote.model.products.product_details

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.SubChoice
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubChoiceResponse(
    val id: Int,
    val name: String,
    val value: String
) : Parcelable {
    fun toSubChoice(): SubChoice {
        return SubChoice(
            id = this.id,
            name = this.name,
            value = this.value,
        )
    }
}