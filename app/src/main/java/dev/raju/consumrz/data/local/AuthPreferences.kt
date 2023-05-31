package dev.raju.consumrz.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import dev.raju.consumrz.utils.Constants.AUTH_KEY
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException

class AuthPreferences(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveEmail(email: String) {
        dataStore.edit { pref ->
            pref[AUTH_KEY] = email
        }
    }

    suspend fun getEmail(): Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[AUTH_KEY]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    suspend fun clearPreferences() {
        dataStore.edit { pref ->
            pref.clear()
        }
    }
}