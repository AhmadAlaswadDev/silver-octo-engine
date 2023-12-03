package com.bestcoders.zaheed.data.remote.model.auth

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.auth.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetUserResponse(
    val success: Boolean,
    val data: UserResponse? = null,
    val message: String? = null,
    val err: Map<String, List<String>>? = null,
) : Parcelable {
    fun toUser(): User {
        return User(
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