package com.bestcoders.zaheed.domain.model.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CartChoiceValue(
    val id: Int,
    val name: String,
    val value: String
)