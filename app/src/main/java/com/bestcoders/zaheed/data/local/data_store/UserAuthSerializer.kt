package com.bestcoders.zaheed.data.local.data_store

import androidx.datastore.core.Serializer
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserAuthSerializer : Serializer<UserAuthDataStoreEntity> {
    override val defaultValue: UserAuthDataStoreEntity
        get() = UserAuthDataStoreEntity()

    override suspend fun readFrom(input: InputStream): UserAuthDataStoreEntity {
        return try {
            Json.decodeFromString(
                deserializer = UserAuthDataStoreEntity.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserAuthDataStoreEntity, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = UserAuthDataStoreEntity.serializer(),
                value= t
            ).encodeToByteArray()
        )
    }

}