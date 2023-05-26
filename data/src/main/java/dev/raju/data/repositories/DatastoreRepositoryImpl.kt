package dev.raju.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import dev.raju.domain.repositories.DatastoreRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * Created by Rajashekhar Vanahalli on 26 May 2023.
 */
class DatastoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DatastoreRepository {

    override suspend fun putBoolean(key: String, value: Boolean) {
        Result.runCatching {
            dataStore.edit { preferences ->
                preferences[booleanPreferencesKey(key)] = value
            }
        }.onFailure {
            println(it.message)
        }
    }

    override suspend fun getBoolean(key: String): Result<Boolean> {
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
                    preferences[booleanPreferencesKey(key)]
                }
            val value = flow.firstOrNull() ?: false // we only care about the 1st value
            value
        }
    }
}