package com.bestcoders.zaheed.data.remote.model.settings

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.settings.PrivacyPolicies
import kotlinx.parcelize.Parcelize

@Parcelize
data class PrivacyPoliciesResponse(
    val success: Boolean,
    val `data`: Data? = null,
    val err: Map<String, List<String>>? = null,
    val code: Int? = null,
) : Parcelable {
    @Parcelize
    data class Data(
        val content: String,
        val title: String
    ) : Parcelable

    fun toPrivacyPolicies(): PrivacyPolicies {
        return PrivacyPolicies(
            title = this.data!!.title,
            content = this.data.content
        )
    }
}