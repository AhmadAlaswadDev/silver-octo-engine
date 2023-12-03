package com.bestcoders.zaheed.presentation.screens.privacy_policies

import com.bestcoders.zaheed.domain.model.settings.PrivacyPolicies

data class PrivacyPoliciesState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val privacyPolicies: PrivacyPolicies? = null,
    val error: String? = null,
)
