package com.bestcoders.zaheed.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.data.local.data_store.SettingsDataStore
import com.bestcoders.zaheed.data.local.data_store.UserAuthSerializer
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


private const val USER_PREFERENCES_NAME = "user_auth_data_store"

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun provideProtoDataStore(@ApplicationContext appContext: Context): DataStore<UserAuthDataStoreEntity> {
        return DataStoreFactory.create(
            serializer = UserAuthSerializer,
            produceFile = { appContext.dataStoreFile(Constants.DATA_STORE_FILE_NAME) },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        )
    }

    @Singleton
    @Provides
    fun provideSettingsDataStore(@ApplicationContext appContext: Context): SettingsDataStore {
        return SettingsDataStore(appContext)
    }
}