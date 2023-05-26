package dev.raju.domain.repositories

import dev.raju.domain.enitities.LoginState
import dev.raju.domain.enitities.SignInParams

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
interface UserRepository {
    suspend fun checkLogin(): LoginState
    suspend fun signIn(params: SignInParams): LoginState
    suspend fun register(params: SignInParams): LoginState
}