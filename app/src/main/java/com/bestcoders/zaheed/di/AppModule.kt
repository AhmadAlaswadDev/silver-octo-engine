package com.bestcoders.zaheed.di

import android.content.Context
import com.bestcoders.zaheed.data.remote.AuthenticationApi
import com.bestcoders.zaheed.data.remote.ProductsApi
import com.bestcoders.zaheed.data.remote.SettingsApi
import com.bestcoders.zaheed.data.repository.AuthRepositoryImpl
import com.bestcoders.zaheed.data.repository.ProductsRepositoryImpl
import com.bestcoders.zaheed.data.repository.SettingsRepositoryImpl
import com.bestcoders.zaheed.domain.repository.AuthRepository
import com.bestcoders.zaheed.domain.repository.ProductsRepository
import com.bestcoders.zaheed.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: AuthenticationApi,
        @ApplicationContext context: Context,
    ): AuthRepository {
        return AuthRepositoryImpl(api = api, context = context)
    }

    @Singleton
    @Provides
    fun provideProductsRepository(
        api: ProductsApi,
        @ApplicationContext context: Context
    ): ProductsRepository {
        return ProductsRepositoryImpl(api, context)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(
        api: SettingsApi,
        @ApplicationContext context: Context
    ): SettingsRepository {
        return SettingsRepositoryImpl(api, context)
    }

}
