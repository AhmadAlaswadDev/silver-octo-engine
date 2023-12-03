package com.bestcoders.zaheed.domain.model.settings


data class Settings(
    val currencies: List<Currency>,
    val currentVersion: String,
    val defaultCurrency: Currency,
    val defaultLanguage: String,
    val languageResponses: List<Language>,
    val layoutsFilters: LayoutsFilters,
    val layoutsFiltersDefaultDiscount: String,
    val layoutsFiltersDefaultMaxPrice: String,
    val layoutsFiltersDefaultMinPrice: String,
    val layoutsFiltersDefaultSortBy: String,
    val layoutsMaxDistanceLimit: String,
    val layoutsPaginationItemsLimit: String,
    val contactEmail: String,
    val orderFiltersStatus: List<OrderStatus>,
    val orderPaymentStatus: List<OrderPaymentStatus>,
)