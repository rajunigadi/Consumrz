package dev.raju.domain.repositories

/**
 * Created by Rajashekhar Vanahalli on 26 May 2023.
 */
interface DatastoreRepository {
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String): Result<Boolean>
}