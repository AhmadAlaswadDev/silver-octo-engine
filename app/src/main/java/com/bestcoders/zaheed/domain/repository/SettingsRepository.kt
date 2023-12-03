package com.bestcoders.zaheed.domain.repository

import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.domain.model.settings.AboutUs
import com.bestcoders.zaheed.domain.model.settings.FAQ
import com.bestcoders.zaheed.domain.model.settings.PrivacyPolicies
import com.bestcoders.zaheed.domain.model.settings.Settings

interface SettingsRepository {

    suspend fun getTermsConditions(): Resource<PrivacyPolicies>

    suspend fun getPrivacy(): Resource<PrivacyPolicies>
    suspend fun getSettings(): Resource<Settings>

    suspend fun getAboutUs(): Resource<AboutUs>
    suspend fun getFAQ(): Resource<FAQ>


}