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
    suspend fun saveAuthToken(loginToken: String) {
        dataStore.edit { pref ->
            pref[AUTH_KEY] = setOf(loginToken)
        }
    }

    suspend fun getAuthToken(): Result<Set<String>> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->
                    /*
                     * dataStore.data throws an IOException when an error
                     * is encountered when reading data
                     */
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    // Get our name value, defaulting to false if not set
                    preferences[AUTH_KEY]
                }
            val value = flow.firstOrNull() ?: emptySet() // we only care about the 1st value
            value
        }
    }
}