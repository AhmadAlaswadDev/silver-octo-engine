package com.bestcoders.zaheed.di

import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.data.remote.AuthenticationApi
import com.bestcoders.zaheed.data.remote.ProductsApi
import com.bestcoders.zaheed.data.remote.SettingsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://dev.zaheed.sa/api/v2/"

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("platform", "android")
                .addHeader("version", "1.0")
                .addHeader("App-Language", Constants.DEFAULT_LANGUAGE)
//                .addHeader("Authorization", Constants.userToken)
                .build()
            chain.proceed(request)
        })
    }.build()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
//            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun provideAuthenticationApi(retrofit: Retrofit): AuthenticationApi {
        return retrofit.create(AuthenticationApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProductsApi(retrofit: Retrofit): ProductsApi {
        return retrofit.create(ProductsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSettingsApi(retrofit: Retrofit): SettingsApi {
        return retrofit.create(SettingsApi::class.java)
    }
}
