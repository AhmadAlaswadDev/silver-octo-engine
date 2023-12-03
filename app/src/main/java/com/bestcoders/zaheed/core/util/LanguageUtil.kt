package com.bestcoders.zaheed.core.util

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.Locale

class LanguageUtil {
    companion object {
        fun changeLanguage(appLanguage:String, context: Context){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (appLanguage == Constants.ARABIC_LANGUAGE_CODE || appLanguage == Constants.SAUDI_LANGUAGE_CODE) {
                    context.getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(Constants.ARABIC_LANGUAGE_CODE)
                    Constants.DEFAULT_LANGUAGE = Constants.SAUDI_LANGUAGE_CODE
                } else {
                    context.getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(Constants.ENGLISH_LANGUAGE_CODE)
                    Constants.DEFAULT_LANGUAGE = Constants.ENGLISH_LANGUAGE_CODE
                }
            } else {
                val locale = Locale(appLanguage)
                Locale.setDefault(locale)
                val resources = context.resources
                val config: Configuration = resources.configuration
                config.setLocale(locale)
                resources.updateConfiguration(config, resources.displayMetrics)
                if (appLanguage == Constants.ARABIC_LANGUAGE_CODE || appLanguage == Constants.SAUDI_LANGUAGE_CODE) {
                    Constants.DEFAULT_LANGUAGE = Constants.SAUDI_LANGUAGE_CODE
                } else {
                    Constants.DEFAULT_LANGUAGE = Constants.ENGLISH_LANGUAGE_CODE
                }
            }
        }
    }

}