package com.bestcoders.zaheed.core.util

sealed interface UiEvent {
    data class ShowSnackbar(val message: String, val action: String? = null) : UiEvent


}
