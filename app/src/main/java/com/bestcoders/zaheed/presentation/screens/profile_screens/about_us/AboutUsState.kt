package com.bestcoders.zaheed.presentation.screens.profile_screens.about_us

import com.bestcoders.zaheed.domain.model.settings.AboutUs

data class AboutUsState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val aboutUs: AboutUs? = null,
    val error: String? = null,
)
