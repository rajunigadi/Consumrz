package dev.raju.domain.repositories

import dev.raju.domain.enitities.SignInParams
import dev.raju.domain.enitities.User
import dev.raju.domain.utils.ResponseCodable

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
interface UserRepository {
    suspend fun checkLogin(): ResponseCodable<Unit>
    suspend fun signIn(params: SignInParams): ResponseCodable<User>
    suspend fun register(params: SignInParams): ResponseCodable<Unit>
}