package com.bestcoders.zaheed.presentation.screens.privacy_policies


sealed interface PrivacyPoliciesEvent {


    data class ShowSnackBar(val message: String) : PrivacyPoliciesEvent

    object GetTermsConditions : PrivacyPoliciesEvent

    object GetPrivacyPolicies : PrivacyPoliciesEvent


}