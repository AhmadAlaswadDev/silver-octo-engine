package com.bestcoders.zaheed.data.remote.model.settings

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.settings.Settings
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingsResponse(
    val success: Boolean,
    val `data`: Data,
    val err: Map<String, List<String>>? = null
) : Parcelable {
    fun toSettings(): Settings {
        return Settings(
            currencies = this.data.currencies.map { it.toCurrency() },
            currentVersion = this.data.current_version,
            defaultCurrency = this.data.default_currency.toCurrency(),
            defaultLanguage = this.data.default_language,
            languageResponses = this.data.languages.map { it.toLanguage() },
            layoutsFilters = this.data.layouts_filters.toLayoutFilter(),
            layoutsFiltersDefaultDiscount = this.data.layouts_filters_default_discount,
            layoutsFiltersDefaultMaxPrice = this.data.layouts_filters_default_max_price,
            layoutsFiltersDefaultMinPrice = this.data.layouts_filters_default_min_price,
            layoutsFiltersDefaultSortBy = this.data.layouts_filters_default_sort_by,
            layoutsMaxDistanceLimit = this.data.layouts_max_distance_limit,
            layoutsPaginationItemsLimit = this.data.layouts_pagination_items_limit,
            contactEmail = this.data.contact_email,
            orderFiltersStatus = this.data.order_filters_status.map { it.toOrderStatus() },
            orderPaymentStatus = this.data.order_payment_status.map { it.toOrderPaymentStatus() },
        )
    }
}