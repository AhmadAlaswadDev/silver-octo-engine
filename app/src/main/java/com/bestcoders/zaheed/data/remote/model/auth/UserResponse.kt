package com.bestcoders.zaheed.data.remote.model.auth

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.auth.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    val id: Int,
    val name: String,
    val type: String,
    val email: String,
    val avatar: String? = null,
    val avatar_original: String,
    val phone: String,
    val saved: String,
    val gender: String?,
    val birth_date: String?,
) : Parcelable {
    fun toUser(): User {
        return User(
            avatar = this.avatar,
            avatarOriginal = this.avatar_original,
            email = this.email,
            id = this.id,
            name = this.name,
            phone = this.phone,
            saved = this.saved,
            type = this.type,
            gender = this.gender,
            birthDate = this.birth_date
        )
    }
}