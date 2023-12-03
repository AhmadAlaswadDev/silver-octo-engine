package com.bestcoders.zaheed.data.remote.model.products.cart

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.CartChoice
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartChoiceResponse(
    val id: Int,
    val name: String,
    val cart_value: CartChoiceValueResponse? = null,
): Parcelable{
    fun toCartChoice():CartChoice {
        return CartChoice(
            id = this.id,
            name = this.name,
            cartValue = this.cart_value?.toCartChoiceValue(),
        )
    }
}