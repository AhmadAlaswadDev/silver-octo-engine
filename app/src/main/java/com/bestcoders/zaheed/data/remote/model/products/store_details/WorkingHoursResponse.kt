package com.bestcoders.zaheed.data.remote.model.products.store_details

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.WorkingHours
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkingHoursResponse(
    val day: String,
    val is_off_all_day: Int? = null,
    val is_open_all_day: Int? = null,
    val phase_1_from: String? = null,
    val phase_1_to: String? = null,
    val phase_2_from: String? = null,
    val phase_2_to: String? = null
) : Parcelable {
    fun toWorkingHours(): WorkingHours {
        return WorkingHours(
            day = this.day,
            isOffAllDay = this.is_off_all_day,
            isOpenAllDay = this.is_open_all_day,
            phase1from = this.phase_1_from ?: "",
            phase1to = this.phase_1_to ?: "",
            phase2from = this.phase_2_from ?: "",
            phase2to = this.phase_2_to ?: "",
        )
    }
}