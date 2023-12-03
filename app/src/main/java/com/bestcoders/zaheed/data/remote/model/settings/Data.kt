package com.bestcoders.zaheed.data.remote.model.settings

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val currencies: List<CurrencyResponse>,
    val current_version: String,
    val default_currency: CurrencyResponse,
    val default_language: String,
    val languages: List<LanguageResponse>,
    val layouts_filters: LayoutsFiltersResponse,
    val layouts_filters_default_discount: String,
    val layouts_filters_default_max_price: String,
    val layouts_filters_default_min_price: String,
    val layouts_filters_default_sort_by: String,
    val layouts_max_distance_limit: String,
    val layouts_pagination_items_limit: String,
    val contact_email: String,
    val order_filters_status: List<OrderStatusResponse>,
    val order_payment_status: List<OrderPaymentStatusResponse>,
) : Parcelable




