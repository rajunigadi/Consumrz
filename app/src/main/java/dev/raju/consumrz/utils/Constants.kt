package dev.raju.consumrz.utils

import androidx.datastore.preferences.core.stringSetPreferencesKey

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
object Constants {
    const val AUTH_PREFERENCES = "AUTH_PREF"
    val AUTH_KEY = stringSetPreferencesKey("auth_key")
}