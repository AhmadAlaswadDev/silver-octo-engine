package com.bestcoders.zaheed.data.remote.model.auth

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.auth.SessionData
import kotlinx.parcelize.Parcelize

@Parcelize
data class SendVerificationCodeResponse(
    val success: Boolean,
    val message: String,
    val err: Map<String, List<String>>? = null,
) : Parcelable {
    fun toSessionData(): SessionData {
        return SessionData(
            success = this.success,
            message = this.message,
        )
    }
}