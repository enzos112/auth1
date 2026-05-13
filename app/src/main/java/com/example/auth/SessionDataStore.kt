package com.example.auth

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore(name = "session")

object SessionDataStore {
    private val SESSION_KEY = booleanPreferencesKey("is_logged_in")

    fun isLoggedIn(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[SESSION_KEY] ?: false }

    suspend fun saveSession(context: Context, loggedIn: Boolean) {
        context.dataStore.edit { it[SESSION_KEY] = loggedIn }
    }
}