package dev.raju.consumrz.domain.usecases

import dev.raju.consumrz.data.local.models.AuthRequest
import dev.raju.consumrz.domain.model.AuthResult
import dev.raju.consumrz.domain.model.SignInResult
import dev.raju.consumrz.domain.model.User
import dev.raju.consumrz.domain.repositories.UserRepository
import dev.raju.consumrz.utils.isValidEmail
import dev.raju.consumrz.utils.isValidPassword

class UserUseCase(
    private val repository: UserRepository
) {
    suspend fun isUserLoggedIn(): AuthResult {
        return AuthResult(
            result = repository.isUserLoggedIn()
        )
    }

    suspend fun logout(): AuthResult {
        return AuthResult(
            result = repository.logout()
        )
    }

    suspend fun signIn(
        email: String,
        password: String
    ): AuthResult {
        val emailError = if (!email.isValidEmail()) "Invalid email address" else null
        val passwordError =
            if (password.isBlank()) "Password cannot be blank" else if (!password.isValidPassword()) "Password should have 4 to 8 characters" else null

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
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        repeatPassword: String
    ): SignInResult {
        val firstNameError = if (firstName.isBlank()) "Firstname cannot be blank" else null
        val lastNameError = if (lastName.isBlank()) "Lastname cannot be blank" else null
        val emailError = if (!email.isValidEmail()) "Invalid email address" else null
        val passwordError =
            if (password.isBlank()) "Password cannot be blank" else if (!password.isValidPassword()) "Password should have 4 to 8 characters" else null
        val repeatPasswordError =
            if (repeatPassword.isBlank()) "Repeat password cannot be blank" else if (!repeatPassword.isValidPassword()) "Repeat password should have 4 to 8 characters" else null

        if (firstNameError != null) {
            return SignInResult(
                firstNameError = firstNameError
            )
        }
        if (lastNameError != null) {
            return SignInResult(
                lastNameError = lastNameError
            )
        }
        if (emailError != null) {
            return SignInResult(
                emailError = emailError
            )
        }

        if (passwordError != null) {
            return SignInResult(
                passwordError = passwordError
            )
        }

        if (repeatPasswordError != null) {
            return SignInResult(
                repeatPasswordError = repeatPasswordError
            )
        }

        if (password != repeatPassword) {
            return SignInResult(
                passwordMatchError = "Password & repeat password not matching"
            )
        }

        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = email.trim(),
            password = password.trim()
        )

        return SignInResult(
            result = repository.register(user)
        )
    }
}
