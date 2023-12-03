package com.bestcoders.zaheed.data.remote.model.auth

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.auth.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthenticationResponse(
    val success: Boolean,
    val message: String,
    val access_token: String? = null,
    val token_type: String? = null,
    val expires_at: String? = null,
    val data: UserResponse? = null,
    val err: Map<String, List<String>>? = null,
) : Parcelable {
    fun toUser(): User {
        return User(
            accessToken = this.access_token,
            expiresAt = this.expires_at,
            tokenType = this.token_type,
            avatar = this.data!!.avatar,
            avatarOriginal = this.data.avatar_original,
            email = this.data.email,
            id = this.data.id,
            name = this.data.name,
            phone = this.data.phone,
            saved = this.data.saved,
            gender = this.data.gender,
            birthDate = this.data.birth_date,
        )
    }
}