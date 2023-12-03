package com.bestcoders.zaheed.data.remote.model.products.cart

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.CartChoiceValue
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartChoiceValueResponse(
    val id: Int,
    val name: String,
    val value: String
) : Parcelable {
    fun toCartChoiceValue(): CartChoiceValue {
        return CartChoiceValue(
            id = this.id,
            name = this.name,
            value = this.value,
        )
    }
}