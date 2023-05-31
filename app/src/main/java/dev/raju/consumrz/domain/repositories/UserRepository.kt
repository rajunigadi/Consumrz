package dev.raju.consumrz.domain.repositories

import dev.raju.consumrz.data.local.models.AuthRequest
import dev.raju.consumrz.domain.model.User
import dev.raju.consumrz.utils.Resource

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
interface UserRepository {
    suspend fun isUserLoggedIn(): Resource<Unit>
    suspend fun login(loginRequest: AuthRequest): Resource<Unit>
    suspend fun logout(): Resource<Unit>
    suspend fun register(user: User): Resource<Unit>
}