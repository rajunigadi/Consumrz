package dev.raju.consumrz.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val CONSUMRZ_DATABASE = "consumrz_db"
    const val AUTH_PREFERENCES = "AUTH_PREF"
    val AUTH_KEY = stringPreferencesKey("auth_key")
}