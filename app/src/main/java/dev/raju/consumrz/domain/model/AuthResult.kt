package dev.raju.consumrz.domain.model

import dev.raju.consumrz.utils.Resource

data class AuthResult(
    val passwordError: String? = null,
    val emailError : String? = null,
    val result: Resource<Unit>? = null
)