package com.bestcoders.zaheed.data.remote.model.settings

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.settings.Language
import kotlinx.parcelize.Parcelize

@Parcelize
data class LanguageResponse(
    val code: String,
    val name: String,
    val rtl: Int
) : Parcelable {
    fun toLanguage(): Language {
        return Language(
            name = this.name,
            code = this.code,
            rtl = this.rtl
        )
    }
}