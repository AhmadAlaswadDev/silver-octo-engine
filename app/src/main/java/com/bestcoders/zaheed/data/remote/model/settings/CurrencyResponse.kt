package com.bestcoders.zaheed.data.remote.model.settings

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.settings.Currency
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyResponse(
    val code: String,
    val id: Int,
    val name: String,
    val symbol: String
): Parcelable {
    fun toCurrency(): Currency {
        return Currency(
            id = this.id,
            name = this.name,
            code = this.code,
            symbol = this.symbol
        )
    }
}