package dev.raju.domain.utils

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
sealed class UiState<out T> {
    object Empty : UiState<Nothing>()

    object Loading : UiState<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : UiState<T>()

    data class Failure(
        val errrors: Array<ErrorCodable>?
    ) : UiState<Nothing>()
}

data class ResponseCodable<T>(
    val data: T? = null,
    val errors: Array<ErrorCodable>? = null
)

data class ErrorCodable(val errorCode: Int, val errorMessage: String) {
    companion object {
        fun defaultErrors(): Array<ErrorCodable> {
            return arrayOf(ErrorCodable(errorCode = 1, errorMessage = "Something went wrong"))
        }

        fun defaultErrors(e: Throwable): Array<ErrorCodable> {
            return arrayOf(ErrorCodable(errorCode = 1, errorMessage = e.message ?: "Something went wrong"))
        }
    }
}