package com.bestcoders.zaheed

import com.bestcoders.zaheed.domain.model.settings.Settings

data class MainState(
    val isLoading: Boolean = false,
    val isUserLoggedIn: Boolean = false,
    val settings: Settings? = null,
    val error: String? = null,
)
