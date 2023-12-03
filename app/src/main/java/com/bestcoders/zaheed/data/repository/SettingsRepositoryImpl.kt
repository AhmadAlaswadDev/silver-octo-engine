package com.bestcoders.zaheed.data.repository

import android.content.Context
import android.util.Log
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.toJson
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.network.getMessageFromRequestCode
import com.bestcoders.zaheed.data.remote.SettingsApi
import com.bestcoders.zaheed.domain.model.settings.AboutUs
import com.bestcoders.zaheed.domain.model.settings.FAQ
import com.bestcoders.zaheed.domain.model.settings.PrivacyPolicies
import com.bestcoders.zaheed.domain.model.settings.Settings
import com.bestcoders.zaheed.domain.repository.SettingsRepository


class SettingsRepositoryImpl(
    private val api: SettingsApi,
    private val context: Context
) : SettingsRepository {

    override suspend fun getTermsConditions(): Resource<PrivacyPolicies> {
        return try {
            val response = api.getTermsConditions()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toPrivacyPolicies())
                } else {
                    Log.e(Constants.EXCEPTION_TAG, body?.err?.toString() + getMessageFromRequestCode(response.code()))
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] } ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getPrivacy(): Resource<PrivacyPolicies> {
        return try {
            val response = api.getPrivacy()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toPrivacyPolicies())
                } else {
                    Log.e(Constants.EXCEPTION_TAG, body?.err?.toString() + getMessageFromRequestCode(response.code()))
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] } ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getSettings(): Resource<Settings> {
        return try {
            val response = api.getSettings()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Log.e("AGUISF", "getSettings: ${body.toSettings().toJson()}", )
                    Resource.Success(body.toSettings())
                } else {
                    Log.e(Constants.EXCEPTION_TAG, body?.err?.toString() + getMessageFromRequestCode(response.code()))
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] } ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getAboutUs(): Resource<AboutUs> {
        return try {
            val response = api.getAboutUs()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toAboutUs())
                } else {
                    Log.e(Constants.EXCEPTION_TAG, body?.message + getMessageFromRequestCode(response.code()))
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] } ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getFAQ(): Resource<FAQ> {
        return try {
            val response = api.getFAQ()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toFAQ())
                } else {
                    Log.e(Constants.EXCEPTION_TAG, body?.message + getMessageFromRequestCode(response.code()))
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] } ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

}
