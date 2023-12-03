package com.bestcoders.zaheed.data.remote.model.settings

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.settings.LayoutsFilters
import kotlinx.parcelize.Parcelize

@Parcelize
data class LayoutsFiltersResponse(
    val amount_of_discount: List<String>,
    val sort_by: List<String>
) : Parcelable {
    fun toLayoutFilter():LayoutsFilters{
        return LayoutsFilters(
            amountOfDiscount = this.amount_of_discount,
            sortBy = this.sort_by
        )
    }
}