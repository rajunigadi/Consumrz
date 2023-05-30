package dev.raju.consumrz.domain.usecases

import dev.raju.consumrz.data.local.models.AuthRequest
import dev.raju.consumrz.domain.model.AuthResult
import dev.raju.consumrz.domain.repositories.UserRepository
import dev.raju.consumrz.utils.DispatcherProvider

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */

class UserUseCase(
    private val repository: UserRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun isUserLoggedIn(): AuthResult {
        return AuthResult(
            result = repository.isUserLoggedIn()
        )
    }

    suspend fun signIn(
        email: String,
        password: String
    ): AuthResult {
        val emailError = if (email.isBlank()) "Username cannot be blank" else null
        val passwordError = if (password.isBlank()) "Password cannot be blank" else null

        if (emailError != null) {
            return AuthResult(
                emailError = emailError
            )
        }

        if (passwordError != null) {
            return AuthResult(
                passwordError = passwordError
            )
        }

        val loginRequest = AuthRequest(
            email = email.trim(),
            password = password.trim()
        )

        return AuthResult(
            result = repository.login(loginRequest)
        )
    }

    suspend fun register(
        email: String,
        password: String,
        repeatPassword: String
    ): AuthResult {
        val emailError = if (email.isBlank()) "Username cannot be blank" else null
        val passwordError = if (password.isBlank()) "Password cannot be blank" else null
        val repeatPasswordError = if (repeatPassword.isBlank()) "Repeat Password cannot be blank" else null

        if (emailError != null) {
            return AuthResult(
                emailError = emailError
            )
        }

        if (passwordError != null) {
            return AuthResult(
                passwordError = passwordError
            )
        }

        if (repeatPasswordError != null) {
            return AuthResult(
                repeatPasswordError = repeatPasswordError
            )
        }

        if(password != repeatPassword) {
            return AuthResult(
                passwordMatchError = "Password & repeat password not matching"
            )
        }

        val registerRequest = AuthRequest(
            email = email.trim(),
            password = password.trim()
        )

        return AuthResult(
            result = repository.register(registerRequest)
        )
    }
}
