package com.bestcoders.zaheed.data.remote

import com.bestcoders.zaheed.data.remote.model.settings.AboutUsResponse
import com.bestcoders.zaheed.data.remote.model.settings.FAQResponse
import com.bestcoders.zaheed.data.remote.model.settings.PrivacyPoliciesResponse
import com.bestcoders.zaheed.data.remote.model.settings.SettingsResponse
import retrofit2.Response
import retrofit2.http.GET

interface SettingsApi {

    @GET("policies/termsConditions")
    suspend fun getTermsConditions(): Response<PrivacyPoliciesResponse>

    @GET("policies/privacy")
    suspend fun getPrivacy(): Response<PrivacyPoliciesResponse>

    @GET("business-settings")
    suspend fun getSettings(): Response<SettingsResponse>

    @GET("about-us")
    suspend fun getAboutUs(): Response<AboutUsResponse>

    @GET("faqs")
    suspend fun getFAQ(): Response<FAQResponse>


}

