package dev.raju.domain.utils

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
sealed class ResponseCodable<out T> {
    object Empty : ResponseCodable<Nothing>()

    object Loading : ResponseCodable<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : ResponseCodable<T>()

    data class Failure(
        val message: String?
    ) : ResponseCodable<Nothing>()
}