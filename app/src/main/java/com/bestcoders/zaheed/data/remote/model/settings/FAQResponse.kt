package com.bestcoders.zaheed.data.remote.model.settings

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.settings.FAQ
import kotlinx.parcelize.Parcelize

@Parcelize
data class FAQResponse(
    val success: Boolean,
    val `data`: FAQData? = null,
    val message: String? = null,
    val err: Map<String, List<String>>? = null,
    val code: Int? = null,
) : Parcelable {
    @Parcelize
    data class FAQData(
        val title: String,
        val content: String
    ) : Parcelable

    fun toFAQ(): FAQ {
        return FAQ(
            title = this.data!!.title,
            content = this.data.content
        )
    }
}