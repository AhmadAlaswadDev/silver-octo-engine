package com.bestcoders.zaheed.data.remote.model.settings

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.settings.AboutUs
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutUsResponse(
    val success: Boolean,
    val `data`: AboutUsData? = null,
    val message: String? = null,
    val err: Map<String, List<String>>? = null,
    val code: Int? = null,
) : Parcelable {
    @Parcelize
    data class AboutUsData(
        val title: String,
        val content: String
    ) : Parcelable

    fun toAboutUs(): AboutUs {
        return AboutUs(
            title = this.data!!.title,
            content = this.data.content
        )
    }
}