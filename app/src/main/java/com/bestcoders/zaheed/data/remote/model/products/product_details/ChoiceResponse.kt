package com.bestcoders.zaheed.data.remote.model.products.product_details

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.Choice
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChoiceResponse(
    val id: Int,
    val name: String,
    val choices: List<SubChoiceResponse>
) : Parcelable {
    fun toChoice(): Choice {
        return Choice(
            id = this.id,
            name  = this.name,
            choices = this.choices.map { it.toSubChoice() }
        )
    }
}