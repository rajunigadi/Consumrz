package dev.raju.data.repositories

import dev.raju.domain.utils.KEY_LOGGED_IN
import dev.raju.data.local.UserDao
import dev.raju.domain.enitities.LoginState
import dev.raju.domain.enitities.SignInParams
import dev.raju.domain.enitities.User
import dev.raju.domain.repositories.DatastoreRepository
import dev.raju.domain.repositories.UserRepository

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
class UserRepositoryImpl(
    private val userDao: UserDao,
    private val datastoreRepository: DatastoreRepository
) : UserRepository {

    override suspend fun checkLogin(): LoginState {
        return LoginState(datastoreRepository.getBoolean(KEY_LOGGED_IN).getOrDefault(false))
    }

    override suspend fun signIn(params: SignInParams): LoginState {
        return LoginState(true)
    }

    override suspend fun register(params: SignInParams): LoginState {
        userDao.addUser(User(params.email, params.password))
        return LoginState(true)
    }
}