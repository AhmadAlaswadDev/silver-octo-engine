package com.bestcoders.zaheed.data.local.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_data")
        private val APP_LANGUAGE = stringPreferencesKey("appLanguage")
    }

    val getAppLanguage: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[APP_LANGUAGE] ?: ""
    }

    suspend fun saveAppLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[APP_LANGUAGE] = language
        }
    }
}