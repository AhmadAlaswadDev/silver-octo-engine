package com.bestcoders.zaheed.presentation.screens.profile_screens.faq

import com.bestcoders.zaheed.domain.model.settings.FAQ

data class FAQState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val faq: FAQ? = null,
    val error: String? = null,
)
