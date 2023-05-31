package dev.raju.consumrz.domain.model

import dev.raju.consumrz.utils.Resource

data class AuthResult(
    val emailError : String? = null,
    val passwordError: String? = null,
    val result: Resource<Unit>? = null
)

data class SignInResult(
    val firstNameError : String? = null,
    val lastNameError : String? = null,
    val emailError : String? = null,
    val passwordError: String? = null,
    val repeatPasswordError: String? = null,
    val passwordMatchError : String? = null,
    val result: Resource<Unit>? = null
)