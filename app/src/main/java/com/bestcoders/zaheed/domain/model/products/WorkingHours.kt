package com.bestcoders.zaheed.domain.model.products


data class WorkingHours(
    val day: String,
    val isOffAllDay: Int? = null,
    val isOpenAllDay: Int? = null,
    val phase1from: String,
    val phase1to: String,
    val phase2from: String,
    val phase2to: String
)