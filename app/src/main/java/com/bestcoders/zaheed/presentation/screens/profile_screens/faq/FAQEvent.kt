package com.bestcoders.zaheed.presentation.screens.profile_screens.faq


sealed interface FAQEvent {
    data class ShowSnackBar(val message: String) : FAQEvent

    object GetFAQ : FAQEvent


}