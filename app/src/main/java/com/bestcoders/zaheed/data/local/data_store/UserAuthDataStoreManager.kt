package com.bestcoders.zaheed.data.local.data_store

import androidx.datastore.core.DataStore
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity


object UserAuthDataStoreManager {
    suspend fun setUserAuthData(
        dataStore: DataStore<UserAuthDataStoreEntity>,
        userAuthDataStoreEntity: UserAuthDataStoreEntity
    ) {
        dataStore.updateData {
            it.copy(
                userToken = userAuthDataStoreEntity.userToken,
                tempToken  = userAuthDataStoreEntity.tempToken,
                name = userAuthDataStoreEntity.name,
                phoneNumber = userAuthDataStoreEntity.phoneNumber,
                saved = userAuthDataStoreEntity.saved,
                email = userAuthDataStoreEntity.email,
                gender = userAuthDataStoreEntity.gender,
                birthDate = userAuthDataStoreEntity.birthDate,
            )
        }
    }
}
